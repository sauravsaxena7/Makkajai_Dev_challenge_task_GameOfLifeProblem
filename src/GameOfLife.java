import java.util.*;


class Cell {
    int x;
    int y;

    public Cell(int x,int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

public class GameOfLife {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Set<Cell> aliveCell = new HashSet<>();

        //User Input
        while(sc.hasNextLine()){

            String input = sc.nextLine();
            String[] inputArray = input.trim().split(", ");
            if(input.isBlank()) break;

            int x = Integer.parseInt(inputArray[0].trim());
            int y = Integer.parseInt(inputArray[1].trim());
            aliveCell.add(new Cell(x,y));

        }
        sc.close();

        //System.out.println(aliveCell);

        Set<Cell> newAliveCell = getNextGenerationForAllCell(aliveCell);
        for(Cell cell:newAliveCell){
            System.out.println(cell.x+", "+ cell.y);
        }
    }

    private static Set<Cell> getNextGenerationForAllCell(Set<Cell> aliveCell) {
        //I am taking this for resultant alive cell after the iteration of each cell
        Set<Cell> newAliveCell = new HashSet<>();

        //This is set of all alive cell as well as all it's neighbours
        Set<Cell> aliveCellIncludingItsAllNeighbour = new HashSet<>();

        //here we are finding all neighbours of aliveCell and added
        // to the aliveCellIncludingItsAllNeighbour set
        for(Cell cell: aliveCell){
            aliveCellIncludingItsAllNeighbour.add(cell);
            aliveCellIncludingItsAllNeighbour.addAll(getNetCurrentCellNeibhour(cell));
        }


        //Now Actual logic start
        for(Cell cell:aliveCellIncludingItsAllNeighbour){

            //for each cell whether it is alive or dead
            // we need to iterate and perform rules operations
            Set<Cell> neighbours = getNetCurrentCellNeibhour(cell);

            //calculating all alive neighbours
            int countOfAliveNebhor=0;
            for (Cell neighbour : neighbours){
                if(aliveCell.contains(neighbour)){
                    countOfAliveNebhor+=1;
                }
            }

            //I am focusing to find all alive cell after passing the rule of aliveness

            //3. Any live cell with two or three live neighbours lives, unchanged, to the next generation.
            //4. Any dead cell with exactly three live neighbours comes to life.

            if(aliveCell.contains(cell)){
                if(countOfAliveNebhor==2 || countOfAliveNebhor==3){
                    newAliveCell.add(cell);
                }
            }else{
                if(countOfAliveNebhor==3){
                    newAliveCell.add(cell);
                }
            }

        }

        //simple return the resultant alive cell
        return newAliveCell;
    }

    private static Set<Cell> getNetCurrentCellNeibhour(Cell cell) {

        Set<Cell> allNeibhourCurrentCell = new HashSet<>();

        //for each cell neighbours be like
        //we have to add like x+nx , y+ny to find all 8 neighbours
//        nx,ny: -1 , -1
//        nx,ny: -1 , 0
//        nx,ny: -1 , 1
//        nx,ny: 0 , -1
//        nx,ny: 0 , 1
//        nx,ny: 1 , -1
//        nx,ny: 1 , 0
//        nx,ny: 1 , 1

        for(int nx=-1;nx<=1;nx++){
            for(int ny=-1;ny<=1;ny++){
                if(nx!=0 || ny!=0){
                    //System.out.println("nx,ny: "+nx+" , "+ny);
                    allNeibhourCurrentCell.add(new Cell(cell.x+nx, cell.y+ny));
                }
            }
        }
        return allNeibhourCurrentCell;
    }
}