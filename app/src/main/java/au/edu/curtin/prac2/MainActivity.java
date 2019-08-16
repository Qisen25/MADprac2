package au.edu.curtin.prac2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button reset, north, west, south, east, options;
    private TextView desc, isTown, health, cash, coords;
    private Player p;
    private GameMap g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerSetup();

        g = GameMap.getInstance();
        g.genLocations();

        viewSetup();

        north.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                int upmove = p.getColLoc() - 1;

                if(upmove > -1)
                {
                    p.setColLoc(upmove);
                }

                printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());
                updatePlayerStats();
            }
        });

        west.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                int lftmove = p.getRowLoc() - 1;

                if(lftmove > -1)
                {
                    p.setRowLoc(lftmove);
                }

                printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());
                updatePlayerStats();
            }
        });

        south.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                int dmove = p.getColLoc() + 1;

                if(dmove < g.getGrid()[p.getRowLoc()].length)
                {
                    p.setColLoc(dmove);
                }

                printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());
                updatePlayerStats();
            }
        });

        east.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                int rtmove = p.getRowLoc() + 1;

                if(rtmove < g.getGrid().length)
                {
                    p.setRowLoc(rtmove);
                }

                printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());
                updatePlayerStats();
            }
        });

        reset.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                p.reset();
                printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());
                updatePlayerStats();
            }
        });

        options.setOnClickListener(new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                optionActivities(p.getRowLoc(), p.getColLoc(), g.getGrid());
            }
        });
    }

    public void printIsTown(int row, int col, Area[][] a)
    {
        if(a[row][col].isTown())
        {
            isTown.setText("Town");
        }
        else
        {
            isTown.setText("Wilderness");
        }

        coords.setText("Coordinates: [" + row + "] [" + col + "]");
    }

    public void optionActivities(int row, int col, Area[][] a)
    {
        if(a[row][col].isTown())
        {
            isTown.setText("Town");
        }
        else
        {
            isTown.setText("Wilderness");
        }
    }

    public void updatePlayerStats()
    {
        health.setText("Health: " + p.getHealth());
        cash.setText("Cash: " + p.getCash());
    }

    public void viewSetup()
    {
        reset = (Button) findViewById(R.id.reset);
        north = (Button) findViewById(R.id.north);
        west = (Button) findViewById(R.id.west);
        south = (Button) findViewById(R.id.south);
        east = (Button) findViewById(R.id.east);
        options = (Button) findViewById(R.id.options);

        desc = (TextView)findViewById(R.id.desc);
        isTown = (TextView)findViewById(R.id.isTown);
        health = (TextView)findViewById(R.id.health);
        cash = (TextView)findViewById(R.id.cash);
        coords = (TextView)findViewById(R.id.coords);
    }

    public void playerSetup()
    {
        p =  Player.getInstance();
        p.hardcodeItems();

        updatePlayerStats();
    }

}
