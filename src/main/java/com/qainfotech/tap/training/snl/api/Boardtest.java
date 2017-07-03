
package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest.*;
import org.testng.annotations.Test;

public class BeforeTest {
	Board board;

	@BeforeClass
	public void loadboard() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		board = new Board();
		// assertThat(board.getUUID().toString().length()).isEqualTo(0);
	}

	// @Test
	public void register_player_should_return_list_of_registered_player()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		board = new Board();

		assertThat(board.registerPlayer("user 1").length()).isEqualTo(1);
		assertThat(board.registerPlayer("user 2").length()).isEqualTo(2);
		assertThat(board.registerPlayer("user 3").length()).isEqualTo(3);
		assertThat(board.registerPlayer("user 4").length()).isEqualTo(4);
		// assertThat(board.registerPlayer("user 5")).isEqualTo(null);

	}

	@Test
	public void delete_player_using_uuid() throws NoUserWithSuchUUIDException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, IOException {
		board = new Board();
		assertThat(board.registerPlayer("user 1").length()).isEqualTo(1);
		assertThat(board.registerPlayer("user 2").length()).isEqualTo(2);
		assertThat(board.registerPlayer("user 3").length()).isEqualTo(3);
		assertThat(board.registerPlayer("user 4").length()).isEqualTo(4);
		UUID uuid = null;
		JSONArray playerArray = new JSONArray();
		playerArray = board.getData().getJSONArray("players");

		for (int i = 0; i < playerArray.length(); i++) {
			JSONObject singlePlayer = playerArray.getJSONObject(i);
			if (singlePlayer.get("name").equals("user 2")) {

				uuid = (UUID) singlePlayer.get("uuid");
				System.out.println(uuid);
			}

		}
		System.out.println(playerArray.toString());
		board.deletePlayer(uuid);
		System.out.println(playerArray.toString());
		// board.deletePlayer(UUID.randomUUID());
		assertThat(board.getData().length()).isEqualTo(3);
		System.out.println(board.getData().length());

	}

	@Test
	public void rollDice_using_uuid() throws InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		board = new Board();
		board.registerPlayer("user 1");
		board.registerPlayer("user 2");
		board.registerPlayer("user 3");
		board.registerPlayer("user 4");
		int w = 1;
		UUID uuid = null;
		JSONArray player = new JSONArray();
		player = board.getData().getJSONArray("players");
		int c = player.length();
		JSONObject objPlayer;

		while (c != 0) {
			for (int i = 0; i < player.length(); i++) {
				player = board.getData().getJSONArray("players");
				objPlayer = player.getJSONObject(i);

				int position = (int) objPlayer.get("position");
				String name = (String) objPlayer.get("name");
				UUID uuid1 = (UUID) objPlayer.get("uuid");

				objPlayer = player.getJSONObject(i);
				JSONObject obj = board.rollDice(uuid1);
				uuid = (UUID) objPlayer.get("uuid");

				String m = (String) obj.get("message");
				String pname = (String) obj.get("playerName");
				int dice = (int) obj.get("dice");
				System.out.println(pname + " with dice " + dice + " " + m);

				if (position == 100) {
					System.out.println(w + " winner is " + name);
					w++;
					board.deletePlayer(uuid1);

					c--;
				}

			}

		}

	}

	@Test(expectedExceptions = InvalidTurnException.class)
	public void to_Check_Invalid_Turn()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, InvalidTurnException, NoUserWithSuchUUIDException {
		Board board = new Board();
		board.registerPlayer("user 1");
		board.registerPlayer("user 2");
		board.rollDice((UUID) ((JSONObject) board.getData().getJSONArray("players").get(1)).get("uuid"));

	}

	@Test(expectedExceptions = NoUserWithSuchUUIDException.class)
	public void invalid_UUID() throws InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {

		Board board = new Board();
		board.registerPlayer("user 1");
		board.registerPlayer("user 2");
		UUID uuid = UUID.randomUUID();
		board.deletePlayer(uuid);// assertThat(board.registerPlayer("user
									// 4").length()).isEqualTo(1);

	}

	@Test(expectedExceptions = PlayerExistsException.class)
	public void Player_exists_exception()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, InvalidTurnException, NoUserWithSuchUUIDException {
		Board board = new Board();
		board.registerPlayer("user 1");
		board.registerPlayer("user 1");
	}

	@Test(expectedExceptions = GameInProgressException.class)

	public void Game_in_progress_exceptionTestCase() throws InvalidTurnException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		Board board = new Board();
		board.registerPlayer("user1");
		board.registerPlayer("user2");
		board.rollDice((UUID) ((JSONObject) board.getData().getJSONArray("players").get(0)).get("uuid"));
		board.registerPlayer("user3");
	}

	@Test(expectedExceptions = MaxPlayersReachedExeption.class)

	public void Max_player_reachedException() throws InvalidTurnException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		Board board = new Board();
		board.registerPlayer("user1");
		board.registerPlayer("user2");
		board.registerPlayer("user3");
		board.registerPlayer("user4");
		board.registerPlayer("user5");
		board.registerPlayer("user6");

	}

	@Test
	public void Ladder_working_orNot() throws InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		Board board = new Board();
		board.registerPlayer("user1");
	//	board.registerPlayer("user2");
//		board.registerPlayer("user3");
		//board.registerPlayer("user4");
		
		UUID uuid = null;
		
		JSONArray player = new JSONArray();
		player = board.getData().getJSONArray("players");
		
		JSONObject objPlayer;

		
			for (int i = 0; i < 1; i++) {
				player = board.getData().getJSONArray("players");
				objPlayer = player.getJSONObject(i);
				uuid=(UUID) objPlayer.get("uuid");

				int position = (int) objPlayer.get("position");
				JSONObject obj = board.rollDice(uuid);
				int position1=obj.getInt("dice");

				//int position = (int) obj.get("position");
				if (position == 1) {
					assertThat(position1 == 7);
				} else if (position == 2) {
					assertThat(position1 == 24);
				} else if (position == 3) {
					assertThat(position1 == 9);
				} else if (position == 4) {
					assertThat(position1 == 10);
				} else if (position == 5) {
					assertThat(position1 == 11);
				} else if (position == 6) {
					assertThat(position1 == 12);
				}

		
		}
	}
}

