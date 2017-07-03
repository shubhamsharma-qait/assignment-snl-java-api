
package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.* ;

import static org.assertj.core.api.Assertions.* ;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest.*;
import org.testng.annotations.Test;

public class BoardTest {
	Board board;

	@BeforeClass
	public void loadboard() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		board = new Board();
		// assertThat(board.getUUID().toString().length()).isEqualTo(0);
	}


	//   @Test 
	   public void register_player_should_return_list_of_registered_player() throws
	   FileNotFoundException, UnsupportedEncodingException,
	   PlayerExistsException, GameInProgressException,
	   MaxPlayersReachedExeption, IOException 
	   { board=new Board();
	   
	   assertThat(board.registerPlayer("user 1").length()).isEqualTo(1);
	   assertThat(board.registerPlayer("user 2").length()).isEqualTo(2);
	   assertThat(board.registerPlayer("user 3").length()).isEqualTo(3);
	   assertThat(board.registerPlayer("user 4").length()).isEqualTo(4);
	   //assertThat(board.registerPlayer("user 5")).isEqualTo(null);
	   
	   
	   
	   
	   }
	   
	   @Test 
	   public void delete_player_using_uuid() throws
	   NoUserWithSuchUUIDException, PlayerExistsException,
	   GameInProgressException, MaxPlayersReachedExeption, IOException
	   {
		   board=new Board();
	   assertThat(board.registerPlayer("user 1").length()).isEqualTo(1);
	   assertThat(board.registerPlayer("user 2").length()).isEqualTo(2);
	   assertThat(board.registerPlayer("user 3").length()).isEqualTo(3);
	   assertThat(board.registerPlayer("user 4").length()).isEqualTo(4);
	   UUID uuid = null; 
	   JSONArray playerArray = new JSONArray();
	   playerArray =  board.getData().getJSONArray("players");
	   
	   for (int i = 0; i < playerArray.length(); i++) 
	   { JSONObject singlePlayer  = playerArray.getJSONObject(i); 
	   if(singlePlayer.get("name").equals("user 2"))
	   {
	   
	   uuid = (UUID) singlePlayer.get("uuid"); System.out.println(uuid); }
	   
	   } 
	   System.out.println(playerArray.toString()); board.deletePlayer(uuid);
	   System.out.println(playerArray.toString());
	   //board.deletePlayer(UUID.randomUUID());
	   assertThat(board.getData().length()).isEqualTo(3);
	   System.out.println(board.getData().length());
	   
	   }
	  

	@Test
	public void rollDice_using_uuid() throws InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		board=new Board();
		board.registerPlayer("user 1");
		board.registerPlayer("user 2");
		board.registerPlayer("user 3");
		board.registerPlayer("user 4");

		UUID uuid = null;
		JSONArray player = new JSONArray();
		player = board.getData().getJSONArray("players");
		int c = player.length();
		while (c != 0) {
			for (int i = 0; i < player.length(); i++) {
				JSONObject objPlayer = player.getJSONObject(i);
				
				
				int position = (int) objPlayer.get("position");
				String name = (String) objPlayer.get("name");
				UUID uuid1=(UUID) objPlayer.get("uuid");
				
				if (position == 100) {
					System.out.println(" winner is " + name);
					
					 board.deletePlayer(uuid1);
					c--;
					

				}

				uuid = (UUID) objPlayer.get("uuid");

				JSONObject obj=   board.rollDice(uuid);
				String m=(String) obj.get("message");
				String pname=(String) obj.get("playerName");
				int dice=(int) obj.get("dice");
				
				System.out.println(pname+" with no."+dice+""+m);
				

			}
			// board.rollDice(uuid);
		}

	}
	@Test(expectedExceptions = InvalidTurnException.class)
	 	public void to_Check_Invalid_Turn()
	 			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
	 			MaxPlayersReachedExeption, IOException, InvalidTurnException, NoUserWithSuchUUIDException
	 {
	 
	 		board.rollDice((UUID) ((JSONObject) board.getData().getJSONArray("players").get(2)).get("uuid"));
	 
	 	}

}
