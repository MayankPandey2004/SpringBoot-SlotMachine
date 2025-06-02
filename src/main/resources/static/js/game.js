// Initialize Pixi.js with dynamic sizing
let app;

function initializePixiApp() {
  // Get the full window dimensions
  const width = window.innerWidth;
  const height = window.innerHeight;

  app = new PIXI.Application({
    width: width,
    height: height,
    backgroundColor: 0x202020,
    resolution: window.devicePixelRatio || 1,
    resizeTo: window, // This enables automatic resizing
  });

  // Make canvas take full screen
  app.view.style.position = "absolute";
  app.view.style.top = "0";
  app.view.style.left = "0";
  app.view.style.width = "100%";
  app.view.style.height = "100%";
  app.view.style.display = "block";

  document.getElementById("gameCanvas").appendChild(app.view);
}

// Initialize the app
initializePixiApp();

// Store current data
let currentData = {
  symbolMatrix: [],
  resultMatrix: [], // List<int[]> of winning paylines
  multiplierMatrix: [],
  balance: 0,
  totalWin: 0,
  freeSpinOccurred: false,
  freeGrids: [], // Array of free spin symbol matrices
  freeResults: [], // Array of free spin results
  freeMults: [], // Array of free spin multiplier matrices
  inFreeSpinMode: false, // Flag to indicate if we're in free spin mode
};

// Free spin control variables
let isPlayingFreeSpins = false;
let currentFreeSpinIndex = -1;

// Auto spin control variables
let isAutoSpinning = false;
let autoSpinCount = 0;
let remainingAutoSpins = 0;
let stopOnFreeSpins = false;
let autoSpinTimeoutId = null;
let autoSpinPausedForFreeSpins = false; // New flag to track if auto spin is paused

// Define paylines matrix (matches Java code)
const paylines = [
  [1, 1, 1, 1, 1], // 0
  [0, 0, 0, 0, 0], // 1
  [2, 2, 2, 2, 2], // 2
  [0, 1, 2, 1, 0], // 3
  [2, 1, 0, 1, 2], // 4
  [1, 0, 0, 0, 1], // 5
  [1, 2, 2, 2, 1], // 6
  [0, 1, 0, 1, 0], // 7
  [2, 1, 2, 1, 2], // 8
  [1, 1, 0, 1, 1], // 9
  [1, 1, 2, 1, 1], // 10
  [0, 1, 1, 1, 0], // 11
  [2, 1, 1, 1, 2], // 12
  [0, 1, 2, 2, 2], // 13
  [2, 1, 0, 0, 0], // 14
  [0, 2, 0, 2, 0], // 15
  [2, 0, 2, 0, 2], // 16
  [0, 2, 2, 2, 0], // 17
  [2, 0, 0, 0, 2], // 18
  [0, 0, 2, 0, 0], // 19
];

// Force Tool static class for managing the force options
class ForceTool {
  static forceOptions = {
    trigger3Scatters: false,
    trigger4Scatters: false,
    trigger5Scatters: false,
    triggerWild: false,
    triggerBigWin: false,
    triggerHugeWin: false,
    triggerMegaWin: false,
  };

  static initialize() {
    const dropdown = document.getElementById("forceToolDropdown");

    dropdown.addEventListener("change", function () {
      ForceTool.reset();

      const selectedValue = this.value;
      if (selectedValue !== "none") {
        ForceTool.forceOptions[selectedValue] = true;

        const statusEl = document.getElementById("forceToolStatus");
        if (statusEl) {
          statusEl.textContent = `Forcing: ${
            this.options[this.selectedIndex].text
          }`;
          statusEl.className = "status-display status-active";
        }
      } else {
        const statusEl = document.getElementById("forceToolStatus");
        if (statusEl) {
          statusEl.textContent = "Ready (Random)";
          statusEl.className = "status-display status-ready";
        }
      }
    });
  }

  static reset() {
    // Reset all options to false
    Object.keys(ForceTool.forceOptions).forEach((key) => {
      ForceTool.forceOptions[key] = false;
    });
  }

  static getForceOptions() {
    return { ...ForceTool.forceOptions };
  }
}

// Bet Tool static class for managing bet amounts
class BetTool {
  static initialize() {
    const dropdown = document.getElementById("betAmountDropdown");

    // Set default value to 1
    dropdown.value = "1";

    dropdown.addEventListener("change", function () {
      const betStatusEl = document.getElementById("betAmountStatus");
      resetBuyButtonValue();
      if (betStatusEl) {
        betStatusEl.textContent = `Bet: $${this.value}`;
        betStatusEl.className = "status-display";
      }
    });

    // Initialize status display
    const betStatusEl = document.getElementById("betAmountStatus");
    if (betStatusEl) {
      betStatusEl.textContent = "Bet: $1";
      betStatusEl.className = "status-display";
    }
  }

  static getBetAmount() {
    const dropdown = document.getElementById("betAmountDropdown");
    return parseInt(dropdown.value, 10);
  }
}

// Auto Spin Tool static class for managing auto spin functionality
class AutoSpinTool {
  static initialize() {
    const autoSpinButton = document.getElementById("autoSpinButton");
    const stopAutoSpinButton = document.getElementById("stopAutoSpinButton");
    const stopOnFreeSpinsCheckbox = document.getElementById(
      "stopOnFreeSpinsCheckbox"
    );

    // Auto spin button event
    autoSpinButton.addEventListener("click", function () {
      if (isAutoSpinning) return;

      const spinCount = prompt("Enter number of auto spins (1-1000):");
      if (spinCount === null) return; // User cancelled

      const count = parseInt(spinCount, 10);
      if (isNaN(count) || count < 1 || count > 1000) {
        alert("Please enter a valid number between 1 and 1000");
        return;
      }

      AutoSpinTool.startAutoSpin(count);
    });

    // Stop auto spin button event
    stopAutoSpinButton.addEventListener("click", function () {
      AutoSpinTool.stopAutoSpin();
    });

    // Checkbox event
    if (stopOnFreeSpinsCheckbox) {
      stopOnFreeSpinsCheckbox.addEventListener("change", function () {
        stopOnFreeSpins = this.checked;
      });
    }
  }

  static startAutoSpin(count) {
    if (currentData.inFreeSpinMode) {
      alert("Cannot start auto spin while in free spin mode");
      return;
    }

    isAutoSpinning = true;
    autoSpinCount = count;
    remainingAutoSpins = count;
    autoSpinPausedForFreeSpins = false;

    // Update UI
    AutoSpinTool.updateAutoSpinUI();
    AutoSpinTool.updateButtonStates();

    // Start the auto spin sequence
    AutoSpinTool.performAutoSpin();
  }

  static stopAutoSpin() {
    isAutoSpinning = false;
    remainingAutoSpins = 0;
    autoSpinPausedForFreeSpins = false;

    // Clear any pending timeout
    if (autoSpinTimeoutId) {
      clearTimeout(autoSpinTimeoutId);
      autoSpinTimeoutId = null;
    }

    // Update UI
    AutoSpinTool.updateAutoSpinUI();
    AutoSpinTool.updateButtonStates();
  }

  static pauseAutoSpinForFreeSpins() {
    autoSpinPausedForFreeSpins = true;

    // Clear any pending timeout
    if (autoSpinTimeoutId) {
      clearTimeout(autoSpinTimeoutId);
      autoSpinTimeoutId = null;
    }

    // Update UI to show paused state
    AutoSpinTool.updateAutoSpinUI();
    AutoSpinTool.updateButtonStates();
  }

  static resumeAutoSpinAfterFreeSpins() {
    if (isAutoSpinning && autoSpinPausedForFreeSpins) {
      autoSpinPausedForFreeSpins = false;

      // Update UI
      AutoSpinTool.updateAutoSpinUI();
      AutoSpinTool.updateButtonStates();

      // Resume auto spinning with a delay
      autoSpinTimeoutId = setTimeout(() => {
        AutoSpinTool.performAutoSpin();
      }, 1500);
    }
  }

  static async performAutoSpin() {
    // Don't continue if we're paused for free spins
    if (autoSpinPausedForFreeSpins) {
      return;
    }

    if (!isAutoSpinning || remainingAutoSpins <= 0) {
      AutoSpinTool.stopAutoSpin();
      return;
    }

    try {
      // Perform the spin (always regular spin for auto spin, never buy feature)
      await fetchAndRenderData(false);

      // Decrease remaining spins
      remainingAutoSpins--;

      // Update UI
      AutoSpinTool.updateAutoSpinUI();

      // Check if free spins were triggered
      if (currentData.freeSpinOccurred) {
        if (stopOnFreeSpins) {
          // Stop auto spin completely
          AutoSpinTool.stopAutoSpin();
          alert("Auto spin stopped: Free spins triggered!");
          return;
        } else {
          // Pause auto spin for free spins
          AutoSpinTool.pauseAutoSpinForFreeSpins();
          return;
        }
      }

      // Continue auto spinning if not stopped or paused
      if (
        isAutoSpinning &&
        remainingAutoSpins > 0 &&
        !autoSpinPausedForFreeSpins
      ) {
        // Add a delay between spins (1.5 seconds)
        autoSpinTimeoutId = setTimeout(() => {
          AutoSpinTool.performAutoSpin();
        }, 1500);
      } else if (remainingAutoSpins <= 0) {
        AutoSpinTool.stopAutoSpin();
      }
    } catch (error) {
      console.error("Error during auto spin:", error);
      AutoSpinTool.stopAutoSpin();
    }
  }

  static updateAutoSpinUI() {
    const autoSpinStatus = document.getElementById("autoSpinStatus");
    if (autoSpinStatus) {
      if (isAutoSpinning) {
        if (autoSpinPausedForFreeSpins) {
          autoSpinStatus.textContent = `Auto Spin Paused (Free Spins): ${remainingAutoSpins} remaining`;
          autoSpinStatus.className = "status-display status-paused";
        } else {
          autoSpinStatus.textContent = `Auto Spinning: ${remainingAutoSpins} remaining`;
          autoSpinStatus.className = "status-display status-processing";
        }
      } else {
        autoSpinStatus.textContent = "Auto Spin Ready";
        autoSpinStatus.className = "status-display status-ready";
      }
    }
  }

  static updateButtonStates() {
    const spinButton = document.getElementById("spinButton");
    const autoSpinButton = document.getElementById("autoSpinButton");
    const stopAutoSpinButton = document.getElementById("stopAutoSpinButton");

    if (isAutoSpinning && !autoSpinPausedForFreeSpins) {
      // During active auto spin
      spinButton.disabled = true;
      autoSpinButton.disabled = true;
      stopAutoSpinButton.disabled = false;
    } else if (isAutoSpinning && autoSpinPausedForFreeSpins) {
      // During paused auto spin (free spins)
      spinButton.disabled = false; // Allow manual free spins
      autoSpinButton.disabled = true;
      stopAutoSpinButton.disabled = false; // Allow stopping auto spin
    } else {
      // Not auto spinning
      spinButton.disabled = false;
      autoSpinButton.disabled = false;
      stopAutoSpinButton.disabled = true;
    }
  }
}

// Create a single game container that will be positioned responsively
const gameContainer = new PIXI.Container();
app.stage.addChild(gameContainer);

// UI containers for responsive positioning
let uiContainer = new PIXI.Container();
app.stage.addChild(uiContainer);

// Helper function to create text labels with responsive positioning
const createLabel = (text, fontSize = 24) => {
  const label = new PIXI.Text(text, {
    fontFamily: "Arial",
    fontSize: fontSize,
    fill: 0xffffff,
    fontWeight: "bold",
  });
  uiContainer.addChild(label);
  return label;
};

// Create UI elements
const balanceLabel = createLabel("Balance: $0");
const winLabel = createLabel("Win: $0");
const freeSpinsLabel = createLabel("", 28);
freeSpinsLabel.style.fill = 0xffcc00; // Gold color for free spins
const freeSpinsCounter = createLabel("", 20);

// Function to position UI elements responsively
function positionUIElements() {
  const screenWidth = app.screen.width;
  const screenHeight = app.screen.height;

  // Position balance label (top right)
  balanceLabel.anchor.set(1, 0);
  balanceLabel.position.set(screenWidth - 20, 20);

  // Position win label (top right, below balance)
  winLabel.anchor.set(1, 0);
  winLabel.position.set(screenWidth - 20, 50);

  // Position free spins label (top center)
  freeSpinsLabel.anchor.set(0.5, 0);
  freeSpinsLabel.position.set(screenWidth / 2, 20);

  // Position free spins counter (bottom left)
  freeSpinsCounter.anchor.set(0, 1);
  freeSpinsCounter.position.set(20, screenHeight - 20);
}

// Function to position game container responsively
function positionGameContainer() {
  const screenWidth = app.screen.width;
  const screenHeight = app.screen.height;

  // Get the actual bounds of the game container content
  const bounds = gameContainer.getLocalBounds();

  // Center the game container on screen using actual content size
  gameContainer.position.set(
    (screenWidth - bounds.width) / 2,
    (screenHeight - bounds.height) / 2
  );
}

// Function to handle window resize
function handleResize() {
  // Position elements responsively
  positionUIElements();
  positionGameContainer();
}

// Add resize listener
window.addEventListener("resize", handleResize);

// Function to update UI elements like balance and win
function updateUIElements(balance, totalWin, freeSpinText = "") {
  balanceLabel.text = `Balance: ${balance}`;
  winLabel.text = `Win: ${totalWin}`;
  freeSpinsLabel.text = freeSpinText;

  // Update free spins counter if we're in free spin mode
  if (currentData.inFreeSpinMode && currentData.freeGrids.length > 0) {
    const remaining = currentData.freeGrids.length - currentFreeSpinIndex - 1;
    freeSpinsCounter.text = `Free Spins: ${remaining}`;
  } else {
    freeSpinsCounter.text = "";
  }

  // Animate the win amount if there's a win
  if (totalWin > 0) {
    // Reset any existing animations
    winLabel.alpha = 1;
    winLabel.scale.set(1);

    // Flash animation for win label (if gsap is available)
    if (typeof gsap !== "undefined") {
      const flashAnimation = () => {
        winLabel.style.fill = 0xffff00; // Yellow

        // Create a flashing effect
        gsap.to(winLabel, {
          alpha: 0.5,
          duration: 0.5,
          repeat: 5,
          yoyo: true,
          onComplete: () => {
            winLabel.style.fill = 0xffffff; // Back to white
            winLabel.alpha = 1;
          },
        });

        // Scale animation
        gsap.to(winLabel.scale, {
          x: 1.2,
          y: 1.2,
          duration: 0.5,
          repeat: 1,
          yoyo: true,
        });
      };

      // Start animation
      flashAnimation();
    }
  }
}

// Function to render the slot game grid with winning paylines and multipliers
function renderGame(symbolMatrix, resultMatrix, multiplierMatrix) {
  // Clear previous content
  while (gameContainer.children[0]) {
    gameContainer.removeChild(gameContainer.children[0]);
  }

  if (!symbolMatrix || symbolMatrix.length === 0) return;

  const rows = symbolMatrix.length;
  const cols = symbolMatrix[0].length;

  // Make cell size responsive to screen size
  const screenWidth = app.screen.width;
  const screenHeight = app.screen.height;

  // Calculate available space (leaving room for UI elements)
  const availableWidth = screenWidth * 0.8; // 80% of screen width
  const availableHeight = screenHeight * 0.7; // 70% of screen height (leaving room for UI)

  // Calculate optimal cell size based on available space
  const maxCellWidth = Math.floor(availableWidth / cols) - 5;
  const maxCellHeight = Math.floor(availableHeight / rows) - 5;

  const cellSize = Math.min(
    maxCellWidth,
    maxCellHeight,
    240 // Maximum cell size cap
  );

  const padding = Math.max(2, Math.floor(cellSize * 0.06)); // Responsive padding

  // Create a graphics object for drawing lines
  const linesGraphics = new PIXI.Graphics();

  // Create a cells container
  const cellsContainer = new PIXI.Container();
  gameContainer.addChild(cellsContainer);

  // Create cells for the grid
  const cells = [];
  for (let i = 0; i < rows; i++) {
    cells[i] = [];
    for (let j = 0; j < cols; j++) {
      const cell = new PIXI.Graphics();

      const symbolValue = symbolMatrix[i][j];

      // Check if the symbol is "BN" and set background color to red
      if (symbolValue === "BN") {
        cell.beginFill(0xe74c3c); // Red color for BN symbols
      } else if (symbolValue === "WC") {
        cell.beginFill(0x51ae79);
      } else {
        cell.beginFill(0x4a4a4a); // Default cell color
      }

      cell.drawRect(0, 0, cellSize, cellSize);
      cell.endFill();
      cell.position.set(j * (cellSize + padding), i * (cellSize + padding));

      // Responsive font size
      const fontSize = Math.max(12, Math.floor(cellSize * 0.3));

      const text = new PIXI.Text(symbolValue, {
        fontFamily: "Arial",
        fontSize: fontSize,
        fill: 0xffffff,
        align: "center",
      });

      text.anchor.set(0.5);
      text.position.set(cellSize / 2, cellSize / 2);
      cell.addChild(text);
      cellsContainer.addChild(cell);
      cells[i][j] = cell;

      // Add multiplier if exists
      if (
        multiplierMatrix &&
        multiplierMatrix[i] &&
        multiplierMatrix[i][j] > 1
      ) {
        const multiplierFontSize = Math.max(10, Math.floor(cellSize * 0.2));
        const multiplierText = new PIXI.Text(`×${multiplierMatrix[i][j]}`, {
          fontFamily: "Arial",
          fontSize: multiplierFontSize,
          fill: 0xffcc00,
          fontWeight: "bold",
        });
        multiplierText.position.set(cellSize - multiplierText.width - 2, 2);
        cell.addChild(multiplierText);
      }
    }
  }

  // Add lines graphics on top of cells
  gameContainer.addChild(linesGraphics);

  // Track cells that need to be darkened (matching elements)
  const matchingCells = new Set();

  // Draw lines for winning paylines
  if (resultMatrix && resultMatrix.length > 0) {
    resultMatrix.forEach((paylineResult) => {
      const paylineId = paylineResult[0];
      const matchCount = paylineResult[1];

      // Get the payline positions from the paylines array
      const payline = paylines[paylineId];

      // Generate a random color for this payline (bright and visible)
      const color = Math.random() * 0xffffff;

      // Draw a line connecting the centers of the cells in this payline
      linesGraphics.lineStyle(
        Math.max(2, Math.floor(cellSize * 0.06)),
        color,
        0.7
      );

      // Calculate center points for each cell in the FULL payline (all columns)
      const points = [];
      for (let col = 0; col < payline.length; col++) {
        const row = payline[col];
        const cellCenterX = cells[row][col].x + cellSize / 2;
        const cellCenterY = cells[row][col].y + cellSize / 2;
        points.push({ x: cellCenterX, y: cellCenterY });

        // Mark the matching cells (only up to matchCount)
        if (col < matchCount) {
          matchingCells.add(`${row},${col}`);
        }
      }

      // Draw the line connecting all points
      if (points.length > 0) {
        linesGraphics.moveTo(points[0].x, points[0].y);

        for (let i = 1; i < points.length; i++) {
          linesGraphics.lineTo(points[i].x, points[i].y);
        }
      }
    });
  }

  // Darken the matching cells
  for (let i = 0; i < rows; i++) {
    for (let j = 0; j < cols; j++) {
      if (matchingCells.has(`${i},${j}`)) {
        const cell = cells[i][j];
        const darkOverlay = new PIXI.Graphics();
        darkOverlay.beginFill(0x222222, 0.7); // Dark gray with some transparency
        darkOverlay.drawRect(0, 0, cellSize, cellSize);
        darkOverlay.endFill();
        cell.addChildAt(darkOverlay, 0);

        // Make the text stand out more on dark background
        const text = cell.getChildAt(1);
        if (text instanceof PIXI.Text) {
          text.style.fill = 0xffffff;
          text.style.fontWeight = "bold";
        }
      }
    }
  }

  // Create an animated effect for winning paylines
  const lineAnimation = () => {
    linesGraphics.alpha = 0.5 + Math.sin(Date.now() / 300) * 0.3;
    requestAnimationFrame(lineAnimation);
  };

  // Start the animation
  lineAnimation();

  // Update positioning after rendering (with a small delay to ensure bounds are calculated)
  setTimeout(() => {
    positionGameContainer();
  }, 10);
}

// Function to calculate total win from result matrix
function calculateTotalWin(resultMatrix) {
  if (!resultMatrix || resultMatrix.length === 0) return 0;

  let totalWin = 0;

  // Sum up all win amounts (index 2 of each payline result)
  resultMatrix.forEach((paylineResult) => {
    if (paylineResult.length >= 3) {
      totalWin += paylineResult[2]; // Index 2 contains the win amount
    }
  });

  return totalWin;
}

// Function to play the next free spin
function playNextFreeSpin() {
  // Increment free spin index
  currentFreeSpinIndex++;

  // Check if we've reached the end of free spins
  if (currentFreeSpinIndex >= currentData.freeGrids.length) {
    // End free spin mode
    currentData.inFreeSpinMode = false;
    isPlayingFreeSpins = false;
    currentFreeSpinIndex = -1;

    // Update UI
    updateUIElements(currentData.balance, 0, "FREE SPINS COMPLETED!");

    // Reset the spin button text
    const spinButton = document.getElementById("spinButton");
    if (spinButton) spinButton.textContent = "SPIN";

    // Check if we need to resume auto spin after free spins
    if (isAutoSpinning && autoSpinPausedForFreeSpins && !stopOnFreeSpins) {
      AutoSpinTool.resumeAutoSpinAfterFreeSpins();
    }

    // Update button states
    AutoSpinTool.updateButtonStates();

    // Clear the message after a few seconds
    setTimeout(() => {
      updateUIElements(currentData.balance, 0, "");
    }, 3000);

    return;
  }

  // Get the current free spin data
  const index = currentFreeSpinIndex;

  // Calculate win for this free spin
  const freeSpinWin = calculateTotalWin(currentData.freeResults[index]);

  // Add the win to balance
  currentData.balance += freeSpinWin;

  // Show the current free spin
  renderGame(
    currentData.freeGrids[index],
    currentData.freeResults[index],
    currentData.freeMults[index]
  );

  // Update display with current free spin info
  updateUIElements(
    currentData.balance,
    freeSpinWin,
    `FREE SPIN ${index + 1}/${
      currentData.freeGrids.length
    } (Win: ${freeSpinWin})`
  );

  // Update the spin button to show remaining free spins
  const spinButton = document.getElementById("spinButton");
  if (spinButton) {
    const remaining = currentData.freeGrids.length - currentFreeSpinIndex - 1;
    spinButton.textContent = `SPIN FREE (${remaining})`;
  }
}

// Function to handle spin button click
async function handleSpin() {
  // Don't allow manual spin during active auto spin (but allow during paused auto spin)
  if (isAutoSpinning && !autoSpinPausedForFreeSpins) {
    return;
  }

  // Disable the spin button temporarily to prevent multiple clicks
  const spinButton = document.getElementById("spinButton");
  const buyButton = document.getElementById("buyFeatureButton");
  const originalBuyDisabled = buyButton ? buyButton.disabled : false;
  const originalDisabled = spinButton ? spinButton.disabled : false;
  if (spinButton) spinButton.disabled = true;
  if (buyButton) buyButton.disabled = true;

  try {
    // Check if we're in free spin mode
    if (currentData.inFreeSpinMode) {
      // Play next free spin
      playNextFreeSpin();
    } else {
      // Regular spin - fetch new data with isBuyFeature = false
      await fetchAndRenderData(false);
    }
  } finally {
    // Re-enable the spin button if appropriate
    if (!isAutoSpinning || autoSpinPausedForFreeSpins) {
      if (spinButton) spinButton.disabled = originalDisabled;
      if (buyButton) buyButton.disabled = originalBuyDisabled;
    }
  }
}

// Function to handle buy feature button click
async function handleBuyFeature() {
  // Don't allow manual spin during active auto spin (but allow during paused auto spin)
  if (isAutoSpinning && !autoSpinPausedForFreeSpins) {
    return;
  }

  // Disable buttons temporarily to prevent multiple clicks
  const spinButton = document.getElementById("spinButton");
  const buyButton = document.getElementById("buyFeatureButton");
  const originalBuyDisabled = buyButton ? buyButton.disabled : false;
  const originalDisabled = spinButton ? spinButton.disabled : false;
  if (spinButton) spinButton.disabled = true;
  if (buyButton) buyButton.disabled = true;

  try {
    // Check if we're in free spin mode
    if (currentData.inFreeSpinMode) {
      // Play next free spin
      playNextFreeSpin();
    } else {
      // Buy feature spin - fetch new data with isBuyFeature = true
      await fetchAndRenderData(true);
    }
  } finally {
    // Re-enable buttons if appropriate
    if (!isAutoSpinning || autoSpinPausedForFreeSpins) {
      if (spinButton) spinButton.disabled = originalDisabled;
      if (buyButton) buyButton.disabled = originalBuyDisabled;
    }
  }
}

async function resetBuyButtonValue() {
  const buyAmount = document.getElementById("buyFeatureButton");
  if (buyAmount) {
    buyAmount.textContent = `Buy Feature ($${BetTool.getBetAmount() * 125})`;
  }
}

// Fetch data from API and update display with Force Tool integration
async function fetchAndRenderData(isBuyFeature = false) {
  try {
    // Don't allow new spins if already in free spin mode (unless it's auto spin or paused auto spin)
    if (currentData.inFreeSpinMode && !isAutoSpinning) {
      return;
    }

    // Update the status to show it's processing (only if not auto spinning)
    const statusEl = document.getElementById("forceToolStatus");
    if (statusEl && !isAutoSpinning) {
      statusEl.textContent = "Processing...";
      statusEl.className = "status-display status-processing";
    }

    // Get force options for this spin
    const forceOptions = ForceTool.getForceOptions();

    // Get bet amount for this spin
    const betAmount = BetTool.getBetAmount();

    const response = await fetch("/api/spin", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        forceTool: forceOptions,
        betAmt: betAmount,
        isBuyFeature: isBuyFeature, // This will be true for buy feature, false for regular spin
      }),
    });

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    const data = await response.json();

    // Update current data
    currentData = {
      ...data,
      inFreeSpinMode: false,
    };

    // Calculate total win from result matrix
    const totalWin = calculateTotalWin(data.resultMatrix);

    // Add the win to balance
    if (totalWin > 0) {
      currentData.balance += totalWin;
    }

    // Render the game grid with winning paylines and multipliers
    renderGame(data.symbolMatrix, data.resultMatrix, data.multiplierMatrix);

    // Update UI elements with balance and win information
    updateUIElements(data.balance, totalWin);

    // Check if free spins were triggered
    if (data.freeSpinOccurred && data.freeGrids && data.freeGrids.length > 0) {
      // Highlight that free spins are triggered
      updateUIElements(
        data.balance,
        totalWin,
        `FREE SPINS TRIGGERED! ${data.freeGrids.length} FREE SPINS AVAILABLE!`
      );

      // Set up for free spins
      currentData.inFreeSpinMode = true;
      currentFreeSpinIndex = -1;
      isPlayingFreeSpins = true;

      // Update the spin button text
      const spinButton = document.getElementById("spinButton");
      if (spinButton)
        spinButton.textContent = `SPIN FREE (${data.freeGrids.length})`;

      // Handle auto spin behavior based on checkbox
      if (isAutoSpinning) {
        if (stopOnFreeSpins) {
          // Auto spin will be stopped in the performAutoSpin function
        } else {
          // Auto spin will be paused in the performAutoSpin function
        }
      }
    }

    // Reset the status after processing (only if not auto spinning)
    if (statusEl && !isAutoSpinning) {
      const dropdown = document.getElementById("forceToolDropdown");
      if (dropdown && dropdown.value !== "none") {
        statusEl.textContent = `Applied: ${
          dropdown.options[dropdown.selectedIndex].text
        }`;
        statusEl.className = "status-display status-active";
      } else {
        statusEl.textContent = "Ready (Random)";
        statusEl.className = "status-display status-ready";
      }
    }
  } catch (error) {
    console.error("Error fetching data:", error);

    // Update status on error (only if not auto spinning)
    if (!isAutoSpinning) {
      const statusEl = document.getElementById("forceToolStatus");
      if (statusEl) {
        statusEl.textContent = "Error!";
        statusEl.className = "status-display status-error";
      }
    }

    // Stop auto spin on error
    if (isAutoSpinning) {
      AutoSpinTool.stopAutoSpin();
    }
  }
}

// Initialize the app when document is ready
document.addEventListener("DOMContentLoaded", function () {
  // Initialize Force Tool
  ForceTool.initialize();

  // Initialize Bet Tool
  BetTool.initialize();

  // Initialize Auto Spin Tool
  AutoSpinTool.initialize();

  // Add button event listeners
  const spinButton = document.getElementById("spinButton");
  const buyButton = document.getElementById("buyFeatureButton");

  if (spinButton) {
    spinButton.addEventListener("click", handleSpin);
  }

  if (buyButton) {
    buyButton.addEventListener("click", handleBuyFeature);
  }

  // Initial positioning
  handleResize();

  // Initial fetch (regular spin)
  fetchAndRenderData(false);
});

// Handle initial resize after everything is loaded
window.addEventListener("load", () => {
  handleResize();
});
