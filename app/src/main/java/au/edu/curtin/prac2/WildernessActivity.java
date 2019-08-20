package au.edu.curtin.prac2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WildernessActivity extends AppCompatActivity
{
    private int itemIndex, playerItemIndex;
    private Area area;
    private List<Item> wildItems;
    private Player player;
    private String wild;

    private Button leave, pickup, drop, pickPrev, pickNext, dropPrev, dropNext;
    private TextView buyPrice, buyDesc, sellPrice, sellDesc, massOrheal, cashHUD, healthHUD, massHUD, sellMass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilderness);

        setupView();
        setupMarket();

        //pickup buttons
        pickPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!wildItems.isEmpty())
                {

                    itemIndex--;

                    //make sure to not go outside list size
                    itemIndex = validateListBoundary(itemIndex, wildItems);

                    updateInventory(wild, buyDesc, massOrheal, itemIndex, wildItems);
                }
            }
        });

        pickNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!wildItems.isEmpty())
                {

                    itemIndex++;

                    itemIndex = validateListBoundary(itemIndex, wildItems);

                    updateInventory(wild, buyDesc, massOrheal, itemIndex, wildItems);
                }
            }
        });

        pickup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!wildItems.isEmpty())
                {

                    Item currItem = wildItems.get(itemIndex);

                    if (currItem instanceof Food)
                    {
                        player.setHealth(((Food) currItem).getHealth());//deduct health
                    }
                    else
                    {
                        player.addItem((Equipment)currItem);
                    }

                    player.setCash(player.getCash() - currItem.getValue());// deduct cash
                    wildItems.remove(itemIndex);
                    itemIndex--;

                    itemIndex = validateListBoundary(itemIndex, wildItems);

                    updateInventory(wild, buyDesc, massOrheal, itemIndex, wildItems);
                    updateInventory("Player Inventory", sellDesc, sellMass, playerItemIndex, player.getEquips());
                    updatePlayerHud();

                }
            }
        });

        //selling buttons
        dropPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!player.getEquips().isEmpty())
                {

                    playerItemIndex--;

                    //make sure to not go outside list size
                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellMass, playerItemIndex, player.getEquips());
                }
            }
        });

        dropNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!player.getEquips().isEmpty())
                {

                    playerItemIndex++;

                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellMass, playerItemIndex, player.getEquips());
                }
            }
        });

        drop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!player.getEquips().isEmpty())
                {

                    Item currItem = player.getEquips().get(playerItemIndex);


                    player.setCash(player.getCash() + (int)((double)currItem.getValue() * 0.75));// deduct cash
                    player.removeItem((Equipment)currItem);
                    playerItemIndex--;
                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());
                    wildItems.add(currItem);

                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellMass, playerItemIndex, player.getEquips());
                    updateInventory(wild, buyDesc, massOrheal, itemIndex, wildItems);
                    updatePlayerHud();
                }
            }
        });

        leave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent returnData = getIntent();
                returnData.putExtra("area", area);
                setResult(RESULT_OK, returnData);

                finish();
            }
        });
    }

    private void setupMarket()
    {
        this.itemIndex = 0;
        this.playerItemIndex = 0;
        Intent intent = getIntent();
        player = Player.getInstance();
        this.wild = "Wilderness";
//        this.currHealth = intent.getDoubleExtra("health", 100.0);
//        this.cash = intent.getIntExtra("cash", 0);
        area = intent.getParcelableExtra("area");
        wildItems = area.getItems();

        updateInventory(wild, buyDesc, massOrheal, itemIndex, wildItems);
        updateInventory("Player Inventory", sellDesc, sellMass, playerItemIndex, player.getEquips());
        updatePlayerHud();
    }

    private void setupView()
    {
        //buttons
        leave = (Button) findViewById(R.id.leave);
        pickup = (Button) findViewById(R.id.pickup);
        drop = (Button) findViewById(R.id.drop);
        pickPrev = (Button) findViewById(R.id.pickPrev);
        pickNext = (Button) findViewById(R.id.pickNext);
        dropPrev = (Button) findViewById(R.id.dropPrev);
        dropNext = (Button) findViewById(R.id.dropNext);

        //text views
        buyDesc = (TextView) findViewById(R.id.buyItem);
        sellDesc = (TextView) findViewById(R.id.sellItem);
        massOrheal = (TextView) findViewById(R.id.massOrheal);
        healthHUD = (TextView) findViewById(R.id.health);
        massHUD = (TextView)findViewById(R.id.carryMass);
        sellMass = (TextView)findViewById(R.id.sellMass);
    }

    private void updateInventory(String type, TextView desc, TextView mass, int index, List<? extends Item> inventory)
    {
        if (!inventory.isEmpty())
        {
            Item item = inventory.get(index);//get item at current index

            desc.setText((index + 1) + ". " + item.getDesc());
            mass.setText(item.displayStat());
        }
        else
        {
            desc.setText(type + " inventory empty");
            mass.setText("-");
        }
    }

    private void updatePlayerHud()
    {
        healthHUD.setText("Health: " + this.player.getHealth());
        massHUD.setText("Carry Mass: " + this.player.getEquipMass());
    }

    //make sure index does not go out of bounds, if not out of bound then keep current index
    private int validateListBoundary(int index, List items)
    {

        if (index > items.size() - 1)
        {
            index = 0;
        }
        else if (index < 0)
        {
            index = items.size() - 1;
        }

        return index;
    }
}
