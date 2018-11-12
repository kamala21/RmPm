package com.example.pele_.kamala21;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class lmmActivity extends Activity implements  View.OnClickListener{
    Button returnButton;
    Button startButton;
    Button increaseButton;
    Button decreaseButton;
    Button addBotButton;
    Button removeBotButton;
    Button addHumanButton;
    Button removeHumanButton;
    TextView difficulty;
    TextView numBots;
    TextView numHumans;
    int numAiInt;
    int numHumanInt;
    int playerIndex;

    FirebaseDatabase database;
    DatabaseReference lobbyRef;
    DatabaseReference numPlayersRef;
    DatabaseReference diffAIRef;
    DatabaseReference gameStateRef;
    DatabaseReference numHumanRef;
    DatabaseReference numAiRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmpm_local_multiplayer_menu);

        returnButton = (Button)findViewById(R.id.lmmReturnButton);
        returnButton.setOnClickListener(this);
        startButton = (Button)findViewById(R.id.lmmStartServerButton);
        startButton.setOnClickListener(this);
        increaseButton = (Button)findViewById(R.id.lmmIncreaseButton);
        increaseButton.setOnClickListener(this);
        decreaseButton = (Button)findViewById(R.id.lmmDecreaseButton);
        decreaseButton.setOnClickListener(this);
        addBotButton = (Button)findViewById(R.id.lmmAddButton);
        addBotButton.setOnClickListener(this);
        removeBotButton = (Button)findViewById(R.id.lmmRemoveButton);
        removeBotButton.setOnClickListener(this);
        addHumanButton = (Button)findViewById(R.id.lmmAddHumanButton);
        addHumanButton.setOnClickListener(this);
        removeHumanButton = (Button)findViewById(R.id.lmmRemoveHumanButton);
        removeHumanButton.setOnClickListener(this);
        difficulty = (TextView)findViewById(R.id.lmmDifficultyBotsText);
        numBots = (TextView)findViewById(R.id.lmmNumBotsText);
        numHumans = (TextView)findViewById(R.id.lmmNumHumanText);
        numAiInt = 0;
        numHumanInt = 3;
        playerIndex = 0;

        database = FirebaseDatabase.getInstance();
        lobbyRef = database.getReference().child("localMultiPlayerLobby");
        gameStateRef = database.getReference().child("localMultiPlayerGameState");

        numPlayersRef = lobbyRef.child("totalNumberOfPlayers");
        numPlayersRef.setValue(Integer.toString(numHumanInt+1));
        diffAIRef = lobbyRef.child("intelligence");
        diffAIRef.setValue("dumb");
        numHumanRef = lobbyRef.child("numHumans");
        numHumanRef.setValue(numHumanInt);
        numAiRef = lobbyRef.child("numAi");
        numAiRef.setValue(numAiInt);
        numPlayersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playerIndex = Integer.parseInt(dataSnapshot.getValue(String.class))+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.lmmReturnButton:
                Intent mmIntent = new Intent(this, mmActivity.class);
                startActivity(mmIntent);
                finish();
                break;
            case R.id.lmmStartServerButton:
                numHumanRef.setValue(0);
                Intent lmgsIntent = new Intent(this, lmgsActivity.class);
                lmgsIntent.putExtra("playerIndex", 0);
                startActivity(lmgsIntent);
                finish();
                break;
            case R.id.lmmJoinButton:
                Intent lmgsJoinIntent = new Intent(this, lmgsActivity.class);
                lmgsJoinIntent.putExtra("playerIndex", playerIndex);
                startActivity(lmgsJoinIntent);
                finish();
                break;
            case R.id.lmmIncreaseButton:
                diffAIRef.setValue("smart");
                difficulty.setText("smart");
                break;
            case R.id.lmmDecreaseButton:
                diffAIRef.setValue("dumb");
                difficulty.setText("dumb");
                break;
            case R.id.lmmAddButton:
                if(numAiInt+numHumanInt < 5){
                    numAiInt++;
                    numBots.setText(Integer.toString(numAiInt));
                    numAiRef.setValue(numAiInt);
                    numPlayersRef.setValue(Integer.toString(numAiInt+numHumanInt+1));
                }
                break;
            case R.id.lmmRemoveButton:
                if(numAiInt+numHumanInt > 1 && numAiInt > 0){
                    numAiInt--;
                    numBots.setText(Integer.toString(numAiInt));
                    numAiRef.setValue(numAiInt);
                    numPlayersRef.setValue(Integer.toString(numAiInt+numHumanInt+1));
                }
                break;
            case R.id.lmmAddHumanButton:
                if(numAiInt+numHumanInt < 5){
                    numHumanInt++;
                    numHumans.setText(Integer.toString(numHumanInt));
                    numHumanRef.setValue(numHumanInt);
                    numPlayersRef.setValue(Integer.toString(numAiInt+numHumanInt+1));
                }
                break;
            case R.id.lmmRemoveHumanButton:
                if(numAiInt+numHumanInt > 1 && numHumanInt > 0){
                    numHumanInt--;
                    numHumans.setText(Integer.toString(numHumanInt));
                    numHumanRef.setValue(numHumanInt);
                    numPlayersRef.setValue(Integer.toString(1));
                }
                break;
            default:
                break;
        }
    }
}
