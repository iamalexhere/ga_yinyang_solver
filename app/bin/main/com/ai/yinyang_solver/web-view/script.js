function convertToBoard() {
	const input = document.getElementById('input').value.trim();
	const lines = input.split('\n');
	const board = document.getElementById('board');
	
	// Clear previous board
	board.innerHTML = '';
	
	if (!lines.length) return;

	const rows = lines.length;
	const cols = lines[0].length;
	
	// Set grid template
	board.style.gridTemplateColumns = `repeat(${cols}, 40px)`;
	
	// Create board cells
	for (let i = 0; i < rows; i++) {
		for (let j = 0; j < cols; j++) {
			const cell = document.createElement('div');
			cell.className = 'cell';
			
			// Add stone if present
			if (lines[i][j] === 'B' || lines[i][j] === 'W') {
				const stone = document.createElement('div');
				stone.className = `stone ${lines[i][j] === 'B' ? 'black' : 'white'}`;
				cell.appendChild(stone);
			}
			
			board.appendChild(cell);
		}
	}
}

// Add example text to textarea on load
window.onload = function() {
	document.getElementById('input').value = 
`B0B0BW
0WBWB0
BWW0BW
BB0WWW
WBB0BW
WW0WWW`;
	convertToBoard();
};