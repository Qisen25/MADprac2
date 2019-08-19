package au.edu.curtin.prac2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MarketActivity extends AppCompatActivity {

    private double currHealth;
    private int cash, itemCount;
    private List<Item> townItems;
    private Button leave, buy, sell, buyPrev, buyNext, sellPrev, sellNext;
    private TextView buyPrice, buyDesc, sellPrice, sellDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        setupView();
        setupMarket();

        buyPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCount--;

                if(itemCount < 0)
                {
                    itemCount = townItems.size() - 1;
                }


                Item item = townItems.get(itemCount);
                updateMarket(item, buyDesc, buyPrice);
            }
        });

        buyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCount++;

                if(itemCount > townItems.size() - 1)
                {
                    itemCount = 0;
                }

                Item item = townItems.get(itemCount);
                updateMarket(item, buyDesc, buyPrice);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupMarket()
    {
        this.itemCount = 0;
        Intent intent = getIntent();
        this.currHealth = intent.getDoubleExtra("health", 100.0);
        this.cash = intent.getIntExtra("cash", 0);
        Area area = intent.getParcelableExtra("area");
        this.townItems = area.getItems();
        updateMarket(townItems.get(itemCount), buyDesc, buyPrice);
    }

    private void setupView()
    {
        //buttons
        leave = (Button) findViewById(R.id.leave);
        buy = (Button) findViewById(R.id.buyButt);
        sell = (Button) findViewById(R.id.sellButt);
        buyPrev = (Button) findViewById(R.id.buyPrev);
        buyNext = (Button) findViewById(R.id.buyNext);
        sellPrev = (Button) findViewById(R.id.sellPrev);
        sellNext = (Button) findViewById(R.id.sellNext);

        //text views
        buyPrice = (TextView)findViewById(R.id.buyPrice);
        buyDesc = (TextView)findViewById(R.id.buyItem);
        sellPrice = (TextView)findViewById(R.id.sellPrice);
        sellDesc = (TextView)findViewById(R.id.sellItem);
    }

    private void updateMarket(Item item, TextView desc, TextView price){
        desc.setText(item.getDesc());
        price.setText("value: " + Double.toString(item.getValue()));
    }
}
