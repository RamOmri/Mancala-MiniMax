import java.util.*;


public class MancalaImp implements MancalaAgent{
    private int maxScore;
    private boolean goAgain;
    int number;

    private boolean endGame(int[] gameState){
        boolean isGameFinished = true;
        if(gameState[6] + gameState[13] == 36) return true;
        for(int i = 0; i < 6; i++){
            if(gameState[i] != 0){
                isGameFinished = false;
            }
        }
       if(isGameFinished) return true;
       isGameFinished = true;
            for(int i = 7; i < 13; i++){
                if(gameState[i] != 0){
                    isGameFinished = false;
                }
            }
      
        return isGameFinished;
    }

    private int[] createNewGameStateMin(int[] currentState, int move){
        int j = currentState[move];
        currentState[move] = 0;
        while(j > 0){
            move++;
            if(move == 6) move = 7;
            if(move == 14) move = 0;
            currentState[move]++;
            j--;
        }
        if(move > 6 && move<13 && currentState[12 - move] > 0 && currentState[move] == 1){   
            currentState[13] += currentState[12 - move] + currentState[move];
            currentState[12 - move] = 0;
            currentState[move] = 0;
        }
        
        if(move == 13) goAgain = true;
        return currentState;
    }
    private int[] createNewGameStateMax(int[] currentState, int move){
        int j = currentState[move];
        currentState[move] = 0;
        while(j > 0){
            move++;
            if(move == 13) move = 0;
            currentState[move]++;
            j--;
        }
        if(move < 6 && currentState[12 - move] > 0 && currentState[move] ==1){ 
            currentState[6] += currentState[12-move] + currentState[move];
            currentState[move] = 0;
            currentState[12-move] = 0;
        }
        if(move == 6) goAgain = true;
        return currentState;
    }
    
    public int[] miniMax(int[] board, boolean maxTurn){
        number++;
        int[] minEval = new int[2];
        minEval[0] = -1;
        
        if(endGame(board) == true){
            int[] endReturn = new int[2]; 
            for(int i = 0; i < 6; i++){ board[6] += board[i];   
                board[i] = 0;

            }
            
            endReturn[0] = board[6];  
            return endReturn;
            
        }
      else if(maxTurn == true){
          int bestMove = -1;
            int[] maxEval = new int[2];
        maxEval[0] = -1;
           for(int i = 5; i >= 0; i--){    
               int[] newState = new int[board.length];
               goAgain = false;
                if(board[i] == 0){
                    continue;}

                newState = createNewGameStateMax(board, i);
                    if(board[i] + i == 6) goAgain = true;
                    int[] eval = new int[2];

                   eval = miniMax(newState, goAgain);

                    if(eval[0] > maxEval[0] || bestMove == -1){ 
                        maxEval[0] = eval[0];   
                        bestMove = i;
                        }
                     
            } 
                maxEval[1] = bestMove; 
                return maxEval; 

        }
        else{
            int bestMove = -1;
                
            for(int i = 12; i > 6; i--){
                    int[] newState = new int[board.length];
                    goAgain = false;
                    if(board[i] == 0) continue;
                    newState = createNewGameStateMin(board, i);
                     

                        
                        int[] eval = new int[2];
                        eval = miniMax(newState, !goAgain);
                        
                        if(minEval[0] > eval[0] || bestMove == -1){
                            bestMove = i;
                            minEval[0] = eval[0];  
                        } 
                      
                     
            }
            minEval[1] = bestMove;  
            return minEval; 
        }
    }

    public int move(int[] board){
        int[] mv = new int[2];
        mv = miniMax(board, true);  System.out.println("Best value: " + mv[0] + " best move: " + mv[1] );
        return mv[1];
        
    }

    public String name(){
            return "MinimaxAgent";
    }

    public void reset(){
        goAgain = false;

    }

    public MancalaImp(){
        goAgain = false;
        number = 0;
    }

   public static void main(String[] args) {
        
    }

   
}