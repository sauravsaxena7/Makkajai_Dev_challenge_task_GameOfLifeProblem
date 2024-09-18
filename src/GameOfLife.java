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
        Set<Cell> newAliveCell = new HashSet<>();
        Set<Cell> aliveCellIncludingItsAllNeighbour = new HashSet<>();

        for(Cell cell: aliveCell){
            aliveCellIncludingItsAllNeighbour.add(cell);
            aliveCellIncludingItsAllNeighbour.addAll(getNetCurrentCellNeibhour(cell));
        }

        for(Cell cell:aliveCellIncludingItsAllNeighbour){
            Set<Cell> neibhors = getNetCurrentCellNeibhour(cell);

            int countOfAliveNebhor=0;
            for (Cell neibhor : neibhors){
                if(aliveCell.contains(neibhor)){
                    countOfAliveNebhor+=1;
                }
            }

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



        return newAliveCell;
    }

    private static Set<Cell> getNetCurrentCellNeibhour(Cell cell) {

        Set<Cell> allNeibhourCurrentCell = new HashSet<>();
        //for each cell neibhour be like
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