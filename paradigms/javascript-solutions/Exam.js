const BoardPrototype = {
    toString: function () {
        return this.getPosition().map(row => row.toString()).join("\n");
    },
    checkEmptyCells: function () {
        let pos = this.getPosition();
        for (let i = 0; i < this.getSizeY(); i++) {
            for (let j = 0; j < this.getSizeX(); i++) {
                if (pos[i][j] === '.') {
                    return true;
                }
            }
        }
        return false;
    },
    checkWin : function(x, y, type) {
        let lc = 0, rc = 0;
        for(let x1 = x; x1 >= 0; x1--) {
            if (this.getPosition()[x1][y] !== type) {
                break;
            }
            lc++;
        }
        for(let x1 = x + 1; x1 < this.getSizeX(); x1++) {
            if (this.getPosition()[x1][y] !== type) {
                break;
            }
            rc++;
        }
        if (rc + lc === 4) {
            return true;
        }
        lc = rc = 0;

        for(let y1 = y; y1 >= 0; y1--) {
            if (this.getPosition()[x][y1] !== type) {
                break;
            }
            lc++;
        }
        for(let y1 = y + 1; y1 < this.getSizeX(); y1++) {
            if (this.getPosition()[x][y1] !== type) {
                break;
            }
            rc++;
        }
        if (rc + lc === 4) {
            return true;
        }

        lc = rc = 0;
        for(let x1 = x, y1 = y; x1 >= 0 && y1 >= 0; x1--, y1--) {
            if (this.getPosition()[x1][y1] !== type) {
                break;
            }
            lc++;
        }
        for(let x1 = x, y1 = y; x1 < this.getSizeX() && y1 < this.getSizeY(); x1++, y1++) {
            if (this.getPosition()[x1][y1] !== type) {
                break;
            }
            rc++;
        }
        if (rc + lc === 4) {
            return true;
        }

        lc = rc = 0;
        for(let x1 = x, y1 = y; x1 >= 0 && y1 < this.getSizeY(); x1--, y1++) {
            if (this.getPosition()[x1][y1] !== type) {
                break;
            }
            lc++;
        }
        for(let x1 = x, y1 = y; x1 < this.getSizeX() && y1 >= 0; x1++, y1--) {
            if (this.getPosition()[x1][y1] !== type) {
                break;
            }
            rc++;
        }
        if (rc + lc === 4) {
            return true;
        }

        return false;
    },
    isValid: function (x, y, type) {
        return x >= 0 && x < this.getSizeX() && y >= 0
            && y < this.getSizeY() && this.getPosition()[x][y] === '.' && !(type === '.');
    },
    makeMove: function (x, y, type) {
        this.getPosition()[x][y] = type;
        console.log(this.toString());
        return this.checkWin(x, y, type);
    }
}

function Board(sizeX, sizeY, ...board) {
    this.getSizeX = function () {
        return sizeX;
    }
    this.getSizeY = function () {
        return sizeY;
    }
    this.getPosition = function () {
        return board
    };
}

Board.prototype = Object.create(BoardPrototype);

function createBoard(sizeX, sizeY) {
    let board = [];
    for (let i = 0; i < sizeY; i++) {
        board[i] = [];
        for (let j = 0; j < sizeX; j++) {
            board[i][j] = '.';
        }
    }
    let tmp = function () {
        Board.call(this, sizeX, sizeY, board);
    }
    return tmp;
}

const PlayerPrototype = {
    makeMove: function () {
        const line = readLine('Player +' + this.getNth() + ' enter your move X, Y, Type: ');
        let input = line.split(' ');
        if (this.getBoard().isValid(input[0], input[1], input[2])) {
            return this.getBoard().makeMove(input[0], input[1], input[2]);
        } else {
            console.log("This move is not valid");
            this.makeMove();
        }
    }
}

function Player(type, nth, board) {
    this.getType = function () {
        return type;
    }
    this.getNth = function () {
        return nth;
    }
    this.getBoard = function () {
        return board;
    }
}

function createPlayer(type, nth) {
    let tmp = function () {
        Player.call(type, nth);
    }
    return tmp;
}

Player.prototype = Object.create(PlayerPrototype);

const GamePrototype = {
    play: function () {
        while (true) {
            if (this.getPlayer1().makeMove()) {
                return 1; // player1 win
            }
            if (this.getPlayer2().makeMove()) {
                return 2; // player2 win
            }
            if (this.getGameBoard().checkEmptyCells()) {
                return 0; // draw
            }
        }
    }
}

function Game(GameBoard, Player1, Player2) {
    this.getGameBoard = function () {
        return GameBoard;
    }
    this.getPlayer1 = function () {
        return Player1;
    }
    this.getPlayer2 = function () {
        return Player2;
    }
}

function createGame(sizeX, sizeY, player1Type, player2Type) {
    let GameBoard = createBoard(sizeX, sizeY);
    let Player1 = createPlayer(player1Type, 1);
    let Player2 = createPlayer(player2Type, 2);
    let tmp = function () {
        Game.call(GameBoard, Player1, Player2);
    }
    return tmp;
}

function main() {
    const line = readLine('Input board size X Y:');
    let size = line.split(' ');
    const line2 = readLine('Input player1 type player2 type:');
    let types = line2.split(' ');
    let mainGame = createGame(size[0], size[1], types[0], types[1]);
    let res = mainGame.play;
    if (res === 0) {
        console.log("Draw");
    } else if (res === 1) {
        console.log("Player1 won");
    } else if (res === 2) {
        console.log("Player2 won");
    }
}

main();