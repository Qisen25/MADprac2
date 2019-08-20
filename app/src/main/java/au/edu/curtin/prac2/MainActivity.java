package au.edu.curtin.prac2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button reset, north, west, south, east, options;
    private TextView desc, isTown, health, cash, coords, massStat;
    private Player p;
    private GameMap g;

    private static final int REQUEST_CODE_MARKET = 0;
    private static final int REQUEST_CODE_WILD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewSetup();
        playerSetup();
        mapSetup();
        printIsTown(p.getRowLoc(), p.getColLoc(), g.getGrid());

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
            Intent intent = new Intent(MainActivity.this, MarketActivity.class);
            intent.putExtra("health", p.getHealth());
            intent.putExtra("cash", p.getCash());
            intent.putExtra("area", a[row][col]);
            startActivityForResult(intent, REQUEST_CODE_MARKET);
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, WildernessActivity.class);
            intent.putExtra("health", p.getHealth());
            intent.putExtra("cash", p.getCash());
            intent.putExtra("area", a[row][col]);
            startActivityForResult(intent, REQUEST_CODE_WILD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK && (requestCode == REQUEST_CODE_MARKET || requestCode == REQUEST_CODE_WILD))
        {
            updatePlayerStats();
            Area area = data.getParcelableExtra("area");
            updateArea(area);
        }
    }

    private void updatePlayerStats()
    {
        health.setText("Health: " + p.getHealth());
        cash.setText("Cash: " + p.getCash());
        massStat.setText("Carry Mass: " + p.getEquipMass());
    }

    private void viewSetup()
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
        massStat = (TextView)findViewById(R.id.sellMass);
    }

    private void playerSetup()
    {
        p =  Player.getInstance();
        p.hardcodeItems();

        updatePlayerStats();
    }

    private void mapSetup()
    {
        g = GameMap.getInstance();
        g.genLocations();
    }

    private void updateArea(Area update)
    {
        g.getGrid()[p.getRowLoc()][p.getColLoc()] = update;
    }
}
