package au.edu.curtin.prac2;

public class GameMap {

    private static GameMap map;

    public static GameMap getInstance()
    {
        if(map == null)
            map = new GameMap();
        return map;
    }


    private Area[][] grid;

    private GameMap() {
        this.grid = new Area[6][6];
    }

    public static GameMap getMap() {
        return map;
    }

    public Area[][] getGrid() {
        return grid;
    }

    public void setGrid(Area[][] grid) {
        this.grid = grid;
    }

    public void genLocations()
    {
        boolean town;
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if((i+j) % 2 == 0)
                {
                    town = true;
                }
                else
                {
                    town = false;
                }

                grid[i][j] = new Area(town);
                grid[i][j].generateItems();
            }
        }
        grid[2][1].addItem(new Equipment("Lute", 98, 10));
        grid[1][1].addItem(new Equipment("Horn", 98, 10));
        grid[1][3].addItem(new Equipment("Book of the Bards", 50, 10));
        grid[4][5].addItem(new Equipment("Lightsaber",100000 , 5));
        grid[3][1].addItem(new Equipment("Robes", 250, 5));

    }
}
