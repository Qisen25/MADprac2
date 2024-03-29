package au.edu.curtin.prac2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MarketActivity extends AppCompatActivity
{

    private int itemIndex, playerItemIndex;
    private Area area;
    private List<Item> townItems;
    private Player player;

    private Button leave, buy, sell, buyPrev, buyNext, sellPrev, sellNext;
    private TextView buyPrice, buyDesc, sellPrice, sellDesc, massOrheal, cashHUD, healthHUD, massHUD, sellMass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        setupView();
        setupMarket();

        //buy buttons
        buyPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!townItems.isEmpty())
                {

                    itemIndex--;

                    //make sure to not go outside list size
                    itemIndex = validateListBoundary(itemIndex, townItems);

                    updateInventory("Town Market", buyDesc, buyPrice, massOrheal, itemIndex, townItems);
                }
            }
        });

        buyNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!townItems.isEmpty())
                {

                    itemIndex++;

                    itemIndex = validateListBoundary(itemIndex, townItems);

                    updateInventory("Town Market", buyDesc, buyPrice, massOrheal, itemIndex, townItems);
                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!townItems.isEmpty())
                {

                    Item currItem = townItems.get(itemIndex);


                    if(player.getCash() > currItem.getValue())
                    {

                        if (currItem instanceof Food)
                        {
                            player.setHealth(((Food) currItem).getHealth());//deduct health
                        }
                        else
                        {
                            player.addItem((Equipment)currItem);
                        }

                        player.setCash(player.getCash() - currItem.getValue());// deduct cash
                        townItems.remove(itemIndex);
                        itemIndex--;

                        itemIndex = validateListBoundary(itemIndex, townItems);

                        updateInventory("Town Market", buyDesc, buyPrice, massOrheal, itemIndex, townItems);
                        updateInventory("Player Inventory", sellDesc, sellPrice, sellMass, playerItemIndex, player.getEquips());
                        updatePlayerHud();
                    }
                    else
                    {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "Not enough cash!", duration);

                        toast.show();
                    }
                }
            }
        });

        //selling buttons
        sellPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!player.getEquips().isEmpty())
                {

                    playerItemIndex--;

                    //make sure to not go outside list size
                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellPrice, sellMass, playerItemIndex, player.getEquips());
                }
            }
        });

        sellNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!player.getEquips().isEmpty())
                {

                    playerItemIndex++;

                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellPrice, sellMass, playerItemIndex, player.getEquips());
                }
            }
        });

        sell.setOnClickListener(new View.OnClickListener()
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
                    townItems.add(currItem);

                    playerItemIndex = validateListBoundary(playerItemIndex, player.getEquips());

                    updateInventory("Player Inventory", sellDesc, sellPrice, sellMass, playerItemIndex, player.getEquips());
                    updateInventory("Town Market", buyDesc, buyPrice, massOrheal, itemIndex, townItems);
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
//        this.currHealth = intent.getDoubleExtra("health", 100.0);
//        this.cash = intent.getIntExtra("cash", 0);
        area = intent.getParcelableExtra("area");
        townItems = area.getItems();

        updateInventory("Town Market", buyDesc, buyPrice, massOrheal, itemIndex, townItems);
        updateInventory("Player Inventory", sellDesc, sellPrice, sellMass, playerItemIndex, player.getEquips());
        updatePlayerHud();
    }

    private void setupView()
    {
        //buttons
        leave = (Button) findViewById(R.id.leave);
        buy = (Button) findViewById(R.id.pickup);
        sell = (Button) findViewById(R.id.drop);
        buyPrev = (Button) findViewById(R.id.pickPrev);
        buyNext = (Button) findViewById(R.id.pickNext);
        sellPrev = (Button) findViewById(R.id.dropPrev);
        sellNext = (Button) findViewById(R.id.dropNext);

        //text views
        buyPrice = (TextView) findViewById(R.id.buyPrice);
        buyDesc = (TextView) findViewById(R.id.buyItem);
        sellPrice = (TextView) findViewById(R.id.sellPrice);
        sellDesc = (TextView) findViewById(R.id.sellItem);
        massOrheal = (TextView) findViewById(R.id.massOrheal);
        cashHUD = (TextView) findViewById(R.id.cash);
        healthHUD = (TextView) findViewById(R.id.health);
        massHUD = (TextView)findViewById(R.id.carryMass);
        sellMass = (TextView)findViewById(R.id.sellMass);
    }

    private void updateInventory(String type, TextView desc, TextView price, TextView mass, int index, List<? extends Item> inventory)
    {
        if (!inventory.isEmpty())
        {
            Item item = inventory.get(index);//get item at current index

            desc.setText((index + 1) + ". " + item.getDesc());
            price.setText("value: " + Double.toString(item.getValue()));
            mass.setText(item.displayStat());
        }
        else
        {
            desc.setText(type + " inventory empty");
            price.setText("0.0");
            mass.setText("-");
        }
    }

    private void updatePlayerHud()
    {
        cashHUD.setText("Cash: " + this.player.getCash());
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
