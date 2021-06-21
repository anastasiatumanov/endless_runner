//Author: Anastasia Tumanov (Engine belongs to Trevor Lane)
//File Name: Main.java
//Project Name: 
//Creation Date: May 26th, 2021 
//Modified Date: TBD
//Description: This is an endless runner type game which involves a female character running through a home, crossing and destroying obstacles such as piles of laundry and mosquitos. The character has three hearts and is able to lose them by not crossing the obstavle successfully. However, the character can collect powerups and powerdowns do boost score and earn lives. 
// Artwork: All artwork except for the background was created by me

// Mr. Lane, this week I worked on improving my "spaghetti code". I was working on making use of methods and introducing power-ups to the gameplay. I also worked on documentation.
//TODO: finish documentation, finish power-ups spawning, complete organizing everything into methods, create progress bar for invincibility power-up

import java.awt.*;
import java.awt.event.KeyEvent;

import Engine.Core.*;
import Engine.Gfx.*;

import java.awt.Rectangle;

public class Main extends AbstractGame
{
  private static final int screenWidth = 800;
  private static final int screenHeight = 480;
  // private static int screenWidth = device.getDisplayMode().getWidth() / 3;
  // private static int screenHeight = device.getDisplayMode().getHeight() / 3;
  private static int windowWidth;
  private static int windowHeight;

  //Store how many milliseconds are in one second
  private static final float SECOND = 1000f;
  private static final int TITLE_BAR_HEIGHT = 25;

  //Game States - Add/Remove/Modify as needed
  //These are the most common game states, but modify as needed
  //You will ALSO need to modify the two switch statements in Update and Draw
  private static final int MENU = 0;
  private static final int INSTRUCTIONS = 2;
  private static final int GAMEPLAY = 3;
  private static final int PAUSE = 4;
  private static final int ENDGAME = 5;
  private static final int STATS = 6;

  //Required Basic Game Visual data used in main below
  private static final String gameName = "Just Another Day";
  private static final int fps = 60;

  //Store and set the initial game state, typically MENU to start
  private int gameState = MENU;

  /////////////////////////////////////////////////////////////////////////////////////
  // Define your Global variables and constants here (They do NOT need to be static) //
  /////////////////////////////////////////////////////////////////////////////////////

  // Three variables for the three types of obstacles
  int MOSQUITO = 1;
  int LAUNDRY = 2;
  int TOYS = 3;
  int COFFEE = 4;
  int PIZZA = 5;
  int WATER = 6;

  //store the amount of time the repeating timer will take before resetting
  final int REPEAT_TIME = 1500;

  //An automatic timer that ticks down to 0, then produces an action and resets (like an alarm clock)
  float repeatingTimer = REPEAT_TIME;

  //A timer used to track the total time passed while in game play (in milliseconds)
  float clockTimer = 0;
  float activatedTimer = 0;

  // Power-ups/power-downs are active for 5 seconds
  final int ACTIVE_TIME = 5000;

  // Maximum rolling pins on screen at once
  final int MAX_ROLLPIN = 20;

  // Create a variable for no object
  final int NO_OBJECT = -1;

  // Max mosquito obstacle on screen
  final int MAX_OBSTACLE = 10;

  // Spawn obstacles every 1.5 seconds
  final float SPAWN_TIME = 1500; // ms
  float spawnTimeLeft = SPAWN_TIME;

  // Spawn obstacles every 1.5 seconds
  final float SPAWN_TIMEP = 10000; // ms
  float spawnTimeLeftP = SPAWN_TIMEP;

  // Variable to increase speed of obstacles every 30 sec
  final float INCREASE_TIME = 30000; // ms
  float increaseTimeLeft = INCREASE_TIME;
  float scrollScreenTimeLeft = INCREASE_TIME;

  // Point awarded every second to the score
  final float AWARD_POINT = 1000; // ms
  float awardTimeLeft = AWARD_POINT;

  // Punch duration
  final float PUNCH_TIMER = 300;
  float repeatPunching = 0;

  // Variables for the different player states
  final int RUN = 0;
  final int JUMP = 1;
  final int PUNCH = 2;

  // Store gravity as a float
  final float GRAVITY = 9f / fps;

  // Scroll screen variable direction
  final int RIGHT = -1;
  final int STOP = 0;

  // Create font for the timer on screen and statistics
  Font hudFont = new Font("Arial", Font.BOLD + Font.ITALIC, 40);
  Font statsFont = new Font("Arial", Font.BOLD + Font.ITALIC, 30);
  Font scoreFont = new Font("Arial", Font.BOLD + Font.ITALIC, 30);

  // Create Vector2F positions for the text during play
  Vector2F secondClockLoc = new Vector2F(0, 40);
  Vector2F scoreHeader = new Vector2F(550, 40);

  // Chance variables for the random number generators
  float chance;
  float chance1;

  // Obstacle/ power-ups speed
  float speed = 4.5f;

  // Mosquito obstacle array
  SpriteSheet[] mosquitos = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] mosquitoPos = new Vector2F[MAX_OBSTACLE];

  // Laundry obstacle array
  SpriteSheet[] laundry = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] laundryPos = new Vector2F[MAX_OBSTACLE];

  // Toys obstacle array
  SpriteSheet[] toys = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] toysPos = new Vector2F[MAX_OBSTACLE];

  // Create SpriteSheet array for character actions/movements
  SpriteSheet[] playerImg = new SpriteSheet[3];

  // Create position for player
  Vector2F playerPos;

  // Position for player speed
  Vector2F playerSpeed = new Vector2F(0f, 0f);

  // Forces variable position
  Vector2F forces = new Vector2F(0, GRAVITY);

  // Jump speed value
  float jumpSpeed = -6.5f;

  // Boolean to check whether character is grounded
  boolean grounded = false;

  // Current playerState
  int playerState = RUN;

  // Spritesheets for coffee power up
  SpriteSheet[] coffee = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] coffeePos = new Vector2F[MAX_OBSTACLE];

  // Spritesheets for pizza power down
  SpriteSheet[] pizza = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] pizzaPos = new Vector2F[MAX_OBSTACLE];

  // Spritesheets for water power up
  SpriteSheet[] water = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] waterPos = new Vector2F[MAX_OBSTACLE];

  // Spritesheet for the rolling pin (weapon)
  SpriteSheet[] rollingPins = new SpriteSheet[MAX_ROLLPIN];
  float pinSpeed = 5f;
  Vector2F[] pinPos = new Vector2F[MAX_ROLLPIN];

  // Spritesheets for all of the heart states
  SpriteSheet oneHeart;
  SpriteSheet twoHeart;
  SpriteSheet threeHeart;

  // Create SpriteSheet for game introduction
  SpriteSheet intro;
  SpriteSheet menuImg;

  // Create SpriteSheet for game instructions
  SpriteSheet instructions;

  // SpriteSheet for the scrolling backgrounds
  SpriteSheet bg1;
  SpriteSheet bg2;

  // Create positions for backgrounds
  Vector2F bg1Pos;
  Vector2F bg2Pos;

  // Game over screen
  SpriteSheet gameOver;

  // Pause screen game
  SpriteSheet pause;

  // Stats game screen
  SpriteSheet stats;

  // Setting variable for scrolling screen
  int xDir = STOP;

  // Variable for background scroll speed
  float scrollSpeed = 4f;

  // User score variable tracks points collected by player
  int score = 0;
  int highScore = 0;

  // User has three hearts, this variable tracks them
  int health = 3;

  // Variables for the invincibility progress bar
  int barWidth = 100;
  GameRectangle timerBarBase;
  GameRectangle timerBarCurrent;

  // Variables for statistics and kill count
  int mosquitoKillCount = 0;
  int laundryKillCount = 0;
  int toyKillCount = 0;

  // Power-ups count for statistics
  int powerUps = 0;

  public static void main(String[] args)
  {
    windowWidth = screenWidth;
    windowHeight = screenHeight - TITLE_BAR_HEIGHT;

    GameContainer gameContainer = new GameContainer(new Main(), gameName, screenWidth, screenHeight, fps);
    gameContainer.Start();
  }

  public void LoadContent(GameContainer gc)
  {
    // Load two backgrounds for scrolling background
    bg1 = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Background.png"));
    bg2 = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Background.png"));

    // Position variables for background
    bg1.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
    bg2.destRec = new Rectangle(windowWidth, 0, windowWidth, windowHeight);
    bg1Pos = new Vector2F(bg1.destRec.x, bg1.destRec.y);
    bg2Pos = new Vector2F(bg2.destRec.x, bg2.destRec.y);

    // Player animation
    playerImg[RUN] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Running1.png"), 2, 5, 0, 8, 8);
    playerImg[JUMP] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Jump.png"));
    playerImg[PUNCH] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Punch.png"));

    // Draw hearts based on player health
    oneHeart = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/OneHeart.png"));
    oneHeart.destRec = new Rectangle(280, 0, (int) (oneHeart.GetFrameWidth() * 0.4), (int) (oneHeart.GetFrameHeight() * 0.4));
    twoHeart = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/TwoHeart.png"));
    twoHeart.destRec = new Rectangle(270, 0, (int) (twoHeart.GetFrameWidth() * 0.3), (int) (twoHeart.GetFrameHeight() * 0.3));
    threeHeart = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/ThreeHeart.png"));
    threeHeart.destRec = new Rectangle(260, 0, (int) (threeHeart.GetFrameWidth() * 0.3), (int) (threeHeart.GetFrameHeight() * 0.3));

    // Loop for loading rolling pin weapons
    for (int i = 0; i < rollingPins.length; i++)
    {
      rollingPins[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/RollingPin.png"));
    }

    // Loop for loading mosquito obstacles
    for (int i = 0; i < mosquitos.length; i++)
    {
      mosquitos[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Mosquito.png"));
    }

    // Loop for loading laundry obstactes
    for (int i = 0; i < laundry.length; i++)
    {
      laundry[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Laundry.png"));
    }

    // Loop for loading toy obstacles
    for (int i = 0; i < toys.length; i++)
    {
      toys[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Toys.png"));
    }

    // Loop for loading coffee power ups
    for (int i = 0; i < coffee.length; i++)
    {
      coffee[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Coffee.png"));
    }

    // Loop for loading pizza power downs
    for (int i = 0; i < pizza.length; i++)
    {
      pizza[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Pizza.png"));
    }

    // Loop for loading water power up
    for (int i = 0; i < water.length; i++)
    {
      water[i] = new SpriteSheet(LoadImage.FromFile("resources/images/sprites/Water.png"));
    }

    // load menu as it is the state we start with
    intro = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/MenuIntro.png"), 8, 5, 0, 40, 10);
    intro.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
    intro.StartAnimation(1);

    // Menu option screen
    menuImg = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Menu.png"));
    menuImg.destRec = new Rectangle(0, 0, windowWidth, windowHeight);

    // Game over screen
    gameOver = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Untitled_Artwork 2.png"));
    gameOver.destRec = new Rectangle(0, 0, windowWidth, windowHeight);

    // Statistics screen
    stats = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Stats.png"));
    stats.destRec = new Rectangle(0, 0, windowWidth, windowHeight);

    // Instructions option
    instructions = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Instructions.png"), 5, 5, 0, 21, 60);
    instructions.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
    instructions.StartAnimation(1);

    // Pause screen
    pause = new SpriteSheet(LoadImage.FromFile("resources/images/backgrounds/Pause.png"));
    pause.destRec = new Rectangle(0, 0, windowWidth, windowHeight);

    //Test your Window size by uncommenting and running this command
    System.out.println("Window Size: " + windowWidth + "x" + windowHeight);
    SetUpGame();
  }

  //Pre: None
  //Post: None
  //Desc: Set up game for beggining of round, reset all variable speeds and positions
  private void SetUpGame()
  {
    System.out.println("Set up game");

    // Set obstacle speeds to original speed
    speed = 4.5f;

    // Reset scroll screen speed
    scrollSpeed = 4f;

    // Reset health and player score
    score = 0;
    health = 3;

    // Reset clock timer to zero
    clockTimer = 0;

    // Set all kill counts to 0
    mosquitoKillCount = 0;
    toyKillCount = 0;
    laundryKillCount = 0;

    // Running player animation - default rectangle
    playerImg[RUN].destRec = new Rectangle(30, 140, playerImg[RUN].GetFrameWidth(), playerImg[RUN].GetFrameHeight());
    playerImg[JUMP].destRec = new Rectangle(30, 140, (playerImg[JUMP].GetFrameWidth()), (playerImg[JUMP].GetFrameHeight()));
    playerImg[PUNCH].destRec = new Rectangle(30, 140, playerImg[PUNCH].GetFrameWidth(), playerImg[PUNCH].GetFrameHeight());

    // Player position variable
    playerPos = new Vector2F(playerImg[RUN].destRec.x, playerImg[RUN].destRec.y);

    // Begin player running animation
    playerImg[RUN].StartAnimation();

    // Mosquito obstacle
    for (int i = 0; i < mosquitos.length; i++)
    {
      // Set a dest rec for mosquito obstacle
      mosquitos[i].destRec = new Rectangle(700, 150, (int) (mosquitos[i].GetFrameWidth() * 0.2), (int) (mosquitos[i].GetFrameHeight() * 0.2));

      // Set mosquito obstacle to transparent
      mosquitos[i].SetVisible(false);

      // Set mosquito position
      mosquitoPos[i] = new Vector2F(mosquitos[i].destRec.x, mosquitos[i].destRec.y);
    }

    // Laundry obstacle setting up
    for (int i = 0; i < laundry.length; i++)
    {
      // Set a dest rec for laundry obstacle
      laundry[i].destRec = new Rectangle(700, 250, (int) (laundry[i].GetFrameWidth() * 0.6), (int) (laundry[i].GetFrameHeight() * 0.6));

      // Set laundry obstacle to transparent
      laundry[i].SetVisible(false);

      // Set laundry position
      laundryPos[i] = new Vector2F(laundry[i].destRec.x, laundry[i].destRec.y);
    }

    // Toy obstacle setting up
    for (int i = 0; i < toys.length; i++)
    {
      // Set a dest rec for toys obstacle
      toys[i].destRec = new Rectangle(700, 320, (int) (toys[i].GetFrameWidth() * 0.15), (int) (toys[i].GetFrameHeight() * 0.15));

      // Set toys obstacle to transparent
      toys[i].SetVisible(false);

      // Set toy position
      toysPos[i] = new Vector2F(toys[i].destRec.x, toys[i].destRec.y);
    }

    // Coffee power-up setting up
    for (int i = 0; i < coffee.length; i++)
    {
      // Set a dest rec for coffee obstacle
      coffee[i].destRec = new Rectangle(700, 150, (int) (coffee[i].GetFrameWidth() * 0.3), (int) (coffee[i].GetFrameHeight() * 0.3));

      // Set coffee power-up to transparent
      coffee[i].SetVisible(false);

      // Set coffee position
      coffeePos[i] = new Vector2F(coffee[i].destRec.x, coffee[i].destRec.y);
    }

    // Pizza power-down setting up
    for (int i = 0; i < pizza.length; i++)
    {
      // Set a dest rec for pizza obstacle
      pizza[i].destRec = new Rectangle(700, 150, (int) (pizza[i].GetFrameWidth() * 0.3), (int) (pizza[i].GetFrameHeight() * 0.3));

      // Set pizza power-down to transparent
      pizza[i].SetVisible(false);

      // Set pizza position
      pizzaPos[i] = new Vector2F(pizza[i].destRec.x, pizza[i].destRec.y);
    }

    // Water power-up setting up
    for (int i = 0; i < water.length; i++)
    {
      // Set a dest rec for water power-ups
      water[i].destRec = new Rectangle(700, 150, (int) (water[i].GetFrameWidth() * 0.3), (int) (water[i].GetFrameHeight() * 0.3));

      // Set water power-up to transparent
      water[i].SetVisible(false);

      // Set water position
      waterPos[i] = new Vector2F(water[i].destRec.x, water[i].destRec.y);
    }

    // Loop for loading rolling pin weapons
    for (int i = 0; i < rollingPins.length; i++)
    {
      // Set a dest rec for rolling pins
      rollingPins[i].destRec = new Rectangle(0, 0, (int) (rollingPins[i].GetFrameWidth() * 0.2), (int) (rollingPins[i].GetFrameHeight() * 0.2));

      // Set rolling pins position
      rollingPins[i].SetVisible(false);
      pinPos[i] = new Vector2F(rollingPins[i].destRec.x, rollingPins[i].destRec.y);
    }

    // Set up timer progress power-up bar
    timerBarBase = new GameRectangle(20, 50, barWidth, 20,2, Helper.WHITE, Helper.DARK_GRAY, 0f);
    timerBarCurrent = new GameRectangle(20, 50, barWidth, 20, 2, Helper.WHITE, Helper.BLUE, 0f);
  }

  public void Update(GameContainer gc, float deltaTime)
  {
    switch (gameState)
    {
      case MENU:
        //Get and implement menu interactions
        UpdateMenu(gc);
        break;
      case INSTRUCTIONS:
        //Get user input to return to MENU
        UpdateInstructions();
        break;
      case GAMEPLAY:
        //Implement standard game logic (input, update game objects, apply physics,
        //collision detection, update HUD elements, etc.)
        UpdateGamePlay(deltaTime);
        break;
      case PAUSE:
        //Get user input to unpause the game
        UpdatePause();
        break;
      case ENDGAME:
        //Wait for final input based on end of game options (end, restart, etc.)
        UpdateEndgame();
        break;
      case STATS:
        UpdateStats();
        break;
      default:
    }
  }

  public void Draw(GameContainer gc, Graphics2D gfx)
  {
    switch (gameState)
    {
      case MENU:
        //Draw the possible menu options
        DrawMenu(gfx);
        break;
      case INSTRUCTIONS:
        //Draw the game instructions including prompt to return to MENU
        DrawInstructions(gfx);
        break;
      case GAMEPLAY:
        //Draw all game objects on each layer (background, middleground, foreground and user interface)
        DrawGamePlay(gfx);
        break;
      case PAUSE:
        //Draw the pause screen, this may include the full game drawing behind
        DrawPause(gfx);
        break;
      case ENDGAME:
        //Draw the final feedback and prompt for avaialable options (exit, restart, etc.)
        DrawEndgame(gfx);
        break;
      case STATS:
        DrawStats(gfx);
        break;
      default:
    }
  }

  //Pre: None
  //Post: None
  //Desc: Method to make it easier to change states, takes in new state and changes it from old state
  private void ChangeState(int newState)
  {
    // Print when states are changed
    System.out.println("Change state from " + gameState + " to " + newState);

    // If the new state is the same as old gamestate
    if (newState == gameState)
    {
      // A warning message will be sent
      System.out.println("Warning: state is already " + newState);
      return;
    }

    // Change game state
    gameState = newState;
  }

  //Pre: None
  //Post: None
  //Desc: Update menu, take in user input to change states
  private void UpdateMenu(GameContainer gc)
  {
    // Change gamestates based on user input
    if (Input.IsKeyPressed(KeyEvent.VK_ENTER))
    {
      // If user presses ENTER, the game begins
      SetUpGame();
      ChangeState(GAMEPLAY);
      return;
    }
    if (Input.IsKeyPressed(KeyEvent.VK_I))
    {
      // If user presses I, the tutorial and instructions are shown
      ChangeState(INSTRUCTIONS);
      return;
    }
    if (Input.IsKeyPressed(KeyEvent.VK_E))
    {
      // If user presses E, the game finishes automatically
      ChangeState(ENDGAME);
      gc.Stop();
    }
  }

  //Pre: None
  //Post: None
  //Desc: Use user input to go from instructions to gameplay
  private void UpdateInstructions()
  {
    // If user presses the enter key
    if (Input.IsKeyDown(KeyEvent.VK_ENTER))
    {
      // If user presses ENTER, the game begins
      ChangeState(GAMEPLAY);
    }
  }

  //Pre: None
  //Post: None
  //Desc: Update all gameplay, manage all interactions
  private void UpdateGamePlay(float deltaTime)
  {
    // Handle user input
    HandleUserInput(deltaTime);

    // Screen scrolls to the right
    xDir = RIGHT;

    //Continue tracking total time passed
    clockTimer += deltaTime;

    //Continue to reduce repeating timer
    repeatingTimer -= deltaTime;
    repeatingTimer = REPEAT_TIME;

    // Use method for scrolling screen
    ScrollScreen(bg1, bg2, bg1Pos, bg2Pos, deltaTime);

    // Translate the rolling pins using a for loop
    for (int i = 0; i < rollingPins.length; i++)
    {
      // If rolling pins at i is visible
      if (rollingPins[i].GetVisible())
      {
        // Move the pin true position at x, as well as the destRec
        pinPos[i].x = rollingPins[i].destRec.x + pinSpeed;
        rollingPins[i].destRec.x = (int) pinPos[i].x;
      }
    }

    // Using a for loop, deactivate bullet if it reaches end of screen
    for (SpriteSheet rollingPin : rollingPins)
    {
      // If the rolling pin at i is already visible
      if (rollingPin.GetVisible())
      {
        // If the rolling pin is greater or equal to the window width
        if (rollingPin.destRec.x >= windowWidth)
        {
          //Deactivate the rolling pin
          rollingPin.SetVisible(false);
        }
      }
    }

    // decide whether we spawn a new obstacle
    spawnTimeLeft = spawnTimeLeft - deltaTime;

    // If the 1.5 seconds have passed
    if (spawnTimeLeft <= 0)
    {
      // Reset the timer variable
      spawnTimeLeft = SPAWN_TIME;

      // 60% chance that a new obstacle will spawn, using a random number generator
      if (Helper.RandomValue(1, 100) <= 60)
      {
        // Spawn one of the obstacles
        SpawnObjects();
      }
    }

    // Move game obstacles
    MoveObjects(mosquitos, mosquitoPos, speed);
    MoveObjects(laundry, laundryPos, speed);
    MoveObjects(toys, toysPos, speed);

    // Deactivate game obstacles when they touch the edge of screen
    DeactivateObject(mosquitos);
    DeactivateObject(laundry);
    DeactivateObject(toys);

    //Add gravity to the player's y Speed (even if not jumping, to allow for falling)
    playerSpeed.y += forces.y;

    // Move game objects by calling method
    MoveGameObjects(playerImg, playerPos, playerSpeed);

    // Method for player top and bottom wall collision
    PlayerWallCollision();

    // Intersection between mosquitoes and player

    if(activatedTimer <= 0)
    {
      ObjectsIntersect(mosquitos, playerState, mosquitoPos);
    }

    // If the playerstate is run and player intersects it, one heart is lost
    if (playerState == RUN && activatedTimer <= 0)
    {
      // Method which handles intersection and damage given (laundry/toys and player)
      ObjectsIntersect(laundry, RUN, laundryPos);
      ObjectsIntersect(toys, RUN, toysPos);
    }

    // If the player state is jump and player jumps over laundry, one heart is lost
    if (playerState == JUMP && activatedTimer <= 0)
    {
      // Method which handles intersection and damage given (laundry and player)
      ObjectsIntersect(laundry, JUMP, laundryPos);
    }

    // If player is punching and activated timer is less than or equal to zero
    if (playerState == PUNCH && activatedTimer <= 0)
    {
      // Method which handles intersection and damage given (toys and player)
      ObjectsIntersect(toys, PUNCH, toysPos);
    }

    // Reduce active ability timer if it is active
    if (activatedTimer > 0)
    {
      // Subtract deltaTime from activated timer
      activatedTimer -= deltaTime;
    }

    // For laundry array, handle intersection between player hit and laundry
    for (int i = 0; i < laundry.length; i++)
    {
      // If laundry is visible
      if (laundry[i].GetVisible())
      {
        // If the player is punching
        if (playerState == PUNCH)
        {
          // If there is an intersection between punching player and laundry
          if (Helper.Intersects(laundry[i].destRec, playerImg[PUNCH].destRec))
          {
            // Set laundry to transparent
            laundry[i].SetVisible(false);

            // Increase the score by 10 pts
            IncreaseBy(10);

            // Reset position of laundry obstacle
            laundry[i].destRec.x = windowWidth;
            laundry[i].destRec.y = 270;
            laundryPos[i].x = laundry[i].destRec.x;
            laundryPos[i].y = laundry[i].destRec.y;

            // Add one point to kill count
            laundryKillCount++;
          }
        }
      }
    }

    // For array of rolling pin weapons
    for (SpriteSheet rollingPin : rollingPins)
    {
      // If the rolling pin at i is visible
      if (rollingPin.GetVisible())
      {
        // For array of mosquito obstacles
        for (int j = 0; j < mosquitos.length; j++)
        {
          // If mosquito is visible
          if (mosquitos[j].GetVisible())
          {
            // If an intersection occurs between the mosquito and rolling pin
            if (Helper.Intersects(mosquitos[j].destRec, rollingPin.destRec))
            {
              // Set mosquito at j to transparent
              mosquitos[j].SetVisible(false);

              // Reset position of mosquito at j
              mosquitos[j].destRec.x = windowWidth;
              mosquitos[j].destRec.y = (int) Helper.RandomValue(50, 150);
              mosquitoPos[j].x = mosquitos[j].destRec.x;
              mosquitoPos[j].y = mosquitos[j].destRec.y;

              // Set rolling pin at i to transparent
              rollingPin.SetVisible(false);

              // Increase total score by ten
              IncreaseBy(10);

              // Add one to the killCount
              mosquitoKillCount++;
            }
          }
        }
      }
    }

    // If health is at zero
    if (health == 0)
    {
      // Change state to endgame, the game is over
      ChangeState(ENDGAME);
    }

    // Subtract deltaTime for faster obstacle spawning
    increaseTimeLeft = increaseTimeLeft - deltaTime;

    // If increase timer is less than or equal to zero
    if (increaseTimeLeft <= 0)
    {
      // Reset variable
      increaseTimeLeft = INCREASE_TIME;

      // Increase all obstacle speeds by one
      speed = speed + 1;
    }

    // Reduce award time left by deltaTime
    awardTimeLeft = awardTimeLeft - deltaTime;

    // If the award time left is less than or equal to zero
    if (awardTimeLeft <= 0)
    {
      // Reset timer
      awardTimeLeft = AWARD_POINT;

      // Increase score by one
      IncreaseBy(1);
    }

    // decide whether we spawn a new obstacle
    spawnTimeLeftP = spawnTimeLeftP - deltaTime;

    // If the 1.5 seconds have passed
    if (spawnTimeLeftP <= 0)
    {
      // Reset the timer variable
      spawnTimeLeftP = SPAWN_TIMEP;

      // 60% chance that a new obstacle will spawn, using a random number generator
      if (Helper.RandomValue(1, 100) <= 70)
      {
        // Spawn one of the obstacles
        SpawnPowerUps();
      }
    }

    // Move power-ups/power-downs
    MoveObjects(coffee, coffeePos, speed);
    MoveObjects(pizza, pizzaPos, speed);
    MoveObjects(water, waterPos, speed);

    // Deactivate objects when they touch the edge of the screen
    DeactivateObject(coffee);
    DeactivateObject(water);
    DeactivateObject(pizza);

    // Coffee power-ups and player jump intersection
    for (int i = 0; i < coffee.length; i++)
    {
      // If coffee at i is visible on screen
      if (coffee[i].GetVisible())
      {
        // If player is jumping
        if (playerState == JUMP)
        {
          // If intersection occurs between jumping player and coffee at i and activated timer is less than or equal to zero
          if (Helper.Intersects(coffee[i].destRec, playerImg[JUMP].destRec) && activatedTimer <= 0)
          {
            // Set coffee object to visible
            coffee[i].SetVisible(false);

            // Move objects to edge of screen
            coffee[i].destRec.x = windowWidth;
            coffee[i].destRec.y = (int) Helper.RandomValue(50, 150);
            coffeePos[i].x = coffee[i].destRec.x;
            coffeePos[i].y = coffee[i].destRec.y;
            powerUps = powerUps + 1;

            // Call methods which give no damage when objects are intersected
            ObjectsIntersectNoDamage(laundry, laundryPos);
            ObjectsIntersectNoDamage(mosquitos, mosquitoPos);
            ObjectsIntersectNoDamage(toys, toysPos);

            // Reset activated timer
            activatedTimer = ACTIVE_TIME;

            // Show the timer bar for user interface
            timerBarBase.SetTransparency(1f);
            timerBarCurrent.SetTransparency(1f);
          }
        }
      }
    }

    // If activated timer is less than or equal to zero
    if (activatedTimer <= 0)
    {
      // Set the bar transparency to 0
      timerBarBase.SetTransparency(0f);
      timerBarCurrent.SetTransparency(0f);
    }

    // Change progress bar for invincibility power-up
    timerBarCurrent.SetWidth(activatedTimer/ACTIVE_TIME * barWidth);

    // Handle pizza and jumping player intersection to lose 50 points
    for (int i = 0; i < pizza.length; i++)
    {
      // If pizza object at i is visible
      if (pizza[i].GetVisible())
      {
        // If player is jumping
        if (playerState == JUMP)
        {
          // If pizza and jumping player intersects
          if (Helper.Intersects(pizza[i].destRec, playerImg[JUMP].destRec))
          {
            // and score is greater than 50
            if (score > 50)
            {
              // Take away 50 points
              IncreaseBy(-50);
            }
            // Set pizza visibility to false
            pizza[i].SetVisible(false);

            // Move pizza to edge of screen
            pizza[i].destRec.x = windowWidth;
            pizza[i].destRec.y = (int) Helper.RandomValue(50, 150);
            pizzaPos[i].x = pizza[i].destRec.x;
            pizzaPos[i].y = pizza[i].destRec.y;
          }
        }
      }
    }

    // Handle water and jumping player intersection to reset all hearts
    for (int i = 0; i < water.length; i++)
    {
      // If water at i is visible
      if (water[i].GetVisible())
      {
        // If the player is jumping
        if (playerState == JUMP)
        {
          // If water power-up intersects with jumping player
          if (Helper.Intersects(water[i].destRec, playerImg[JUMP].destRec))
          {
            // Set water at i to transparent
            water[i].SetVisible(false);

            // Reset health to three hearts
            health = 3;

            // Change position of water at i
            water[i].destRec.x = windowWidth;
            water[i].destRec.y = (int) Helper.RandomValue(50, 150);
            waterPos[i].x = water[i].destRec.x;
            waterPos[i].y = water[i].destRec.y;

            // Increase power-ups counter by one for statistics
            powerUps = powerUps + 1;
          }
        }
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Handle user input during gameplay, all player states (jumping, running, punching and throwing)
  private void HandleUserInput(float deltaTime)
  {
    // Jump if the player hits space and is on the ground
    if (Input.IsKeyPressed(KeyEvent.VK_SPACE) && grounded)
    {
      // Player speed in the y direction is the set jump speed
      playerSpeed.y = jumpSpeed;

      // Change player state to jump
      playerState = JUMP;

      // Stop the run animation
      playerImg[RUN].StopAnimation();
    }
    if (Input.IsKeyReleased(KeyEvent.VK_P))
    {
      // If user presses p, the game is paused
      ChangeState(PAUSE);
      return;
    }
    //Hold the H key to hit, release to return to RUN
    if (Input.IsKeyPressed(KeyEvent.VK_H) && grounded && repeatPunching <= 0)
    {
      // Change player state to punch
      playerState = PUNCH;

      // Stop running animation
      playerImg[RUN].StopAnimation();

      // Set timer to full time to begin the count down
      repeatPunching = PUNCH_TIMER;
    }

    // If repeatPunching timer is greater than zero
    if (repeatPunching > 0)
    {
      // Subtract deltaTime
      repeatPunching -= deltaTime;

      // If repeat punching is smaller then zero
      if (repeatPunching < 0)
      {
        // Set player state to run
        playerState = RUN;

        // Start the run animation
        playerImg[RUN].StartAnimation();
      }
    }

    //Shoot a bullet on the space key if one is available
    if (Input.IsKeyReleased(KeyEvent.VK_T))
    {
      // Find an inactive object (rolling pins) using method
      int spawnId = FindInactiveObject(rollingPins);

      // If the spawn index does not equal to -1, enter the if statement
      if (spawnId != NO_OBJECT)
      {
        // Set rolling pins at the spawn index to visible
        rollingPins[spawnId].SetVisible(true);

        // Set x and y coordinates of the rolling pin
        rollingPins[spawnId].destRec.x = playerImg[RUN].destRec.x + playerImg[RUN].GetFrameWidth();
        rollingPins[spawnId].destRec.y = playerImg[RUN].destRec.y + playerImg[RUN].GetFrameHeight() / 2 - rollingPins[spawnId].GetFrameHeight() / 2;

        // Change pin true position using its destRec
        pinPos[spawnId].x = rollingPins[spawnId].destRec.x;
        pinPos[spawnId].y = rollingPins[spawnId].destRec.y;
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Use user input to update pause state and change to new states
  private void UpdatePause()
  {
    // Change gamestates based on user input
    if (Input.IsKeyDown(KeyEvent.VK_ENTER))
    {
      // If user presses SPACE, the game begins
      ChangeState(GAMEPLAY);
      return;
    }
    if (Input.IsKeyPressed(KeyEvent.VK_I))
    {
      // If user presses I, the tutorial and instructions are shown
      ChangeState(INSTRUCTIONS);
      return;
    }
    if (Input.IsKeyPressed(KeyEvent.VK_M))
    {
      // If user presses E, the game finishes automatically
      ChangeState(MENU);
      SetUpGame();
    }
  }

  //Pre: None
  //Post: None
  //Desc: Update endgame state with user input and change states
  private void UpdateEndgame()
  {
    // Change gamestates based on user input
    if (Input.IsKeyDown(KeyEvent.VK_ENTER))
    {
      ChangeState(MENU);
      SetUpGame();
      return;
    }
    // If user presses "S", change game state to stats
    if (Input.IsKeyDown(KeyEvent.VK_S))
    {
      ChangeState(STATS);
    }
  }

  //Pre: None
  //Post: None
  //Desc: Update statistics state to change to menu
  private void UpdateStats()
  {
    if (Input.IsKeyDown(KeyEvent.VK_M))
    {
      ChangeState(MENU);
      SetUpGame();
    }
  }

  //Pre: None
  //Post: None
  //Desc: Draw menu state and change animation
  private void DrawMenu(Graphics2D gfx)
  {
    // Draw intro sprite animation
    Draw.Sprite(gfx, intro);

    // If intro stops animating, show static menu screen
    if (!intro.IsAnimating())
    {
      // Draw menu screen
      Draw.Sprite(gfx, menuImg);
    }
  }

  //Pre: None
  //Post: None
  //Desc: Draw instructions game screen
  private void DrawInstructions(Graphics2D gfx)
  {
    // Draw game instructions
    Draw.Sprite(gfx, instructions);
  }

  //Pre: None
  //Post: None
  //Desc: Draw all gameplay components including background, obstacles, player and power-ups
  private void DrawGamePlay(Graphics2D gfx)
  {
    // Draw both backgrounds for scrolling screen
    Draw.Sprite(gfx, bg1);
    Draw.Sprite(gfx, bg2);

    // Draw player at current player state
    Draw.Sprite(gfx, playerImg[playerState]);

    // Based on health count, draw the hearts accordingly
    if (health == 3)
    {
      Draw.Sprite(gfx, threeHeart);
    }
    if (health == 2)
    {
      Draw.Sprite(gfx, twoHeart);
    }
    if (health == 1)
    {
      Draw.Sprite(gfx, oneHeart);
    }

    // Draw rolling pins for weapon
    for (SpriteSheet rollingPin : rollingPins)
    {
      Draw.Sprite(gfx, rollingPin);
    }

    // Draw mosquitoes for obstacle
    for (SpriteSheet mosquito : mosquitos)
    {
      if (mosquito.GetVisible())
      {
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.GetFrameWidth(), s.GetFrameHeight(), 3, Color.CYAN, 1f);
        Draw.Sprite(gfx, mosquito);
      }
    }

    // Draw laundry piles for obstacles
    for (SpriteSheet spriteSheet : laundry)
    {
      if (spriteSheet.GetVisible())
      {
        Draw.Sprite(gfx, spriteSheet);
      }
    }

    // Draw toy piles for obstacles
    for (SpriteSheet toy : toys)
    {
      if (toy.GetVisible())
      {
        Draw.Sprite(gfx, toy);
      }
    }

    // Draw coffee cups for power-ups
    for (SpriteSheet spriteSheet : coffee)
    {
      if (spriteSheet.GetVisible())
      {
        Draw.Sprite(gfx, spriteSheet);
      }
    }

    // Draw pizza for power-downs
    for (SpriteSheet spriteSheet : pizza)
    {
      if (spriteSheet.GetVisible())
      {
        Draw.Sprite(gfx, spriteSheet);
      }
    }

    // Draw water glasses for power-ups
    for (SpriteSheet spriteSheet : water)
    {
      if (spriteSheet.GetVisible())
      {
        Draw.Sprite(gfx, spriteSheet);
      }
    }

    //Display the total time passed in full seconds
    Draw.Text(gfx, "Time: " + (int) (clockTimer / SECOND) + "s", secondClockLoc.x, secondClockLoc.y, hudFont, Helper.BLUE, 1f);

    // Display user score
    Draw.Text(gfx, "Score: " + score, scoreHeader.x, scoreHeader.y, scoreFont, Helper.BLUE, 1f);

    // Display session high score
    Draw.Text(gfx, "High Score: " + highScore, scoreHeader.x, scoreHeader.y + 40, scoreFont, Helper.BLUE, 1f);

    // Draw progress bar for invincibility power-up
    timerBarBase.Draw(gfx);
    timerBarCurrent.Draw(gfx);
  }

  //Pre: None
  //Post: None
  //Desc: Draw pause game screen
  private void DrawPause(Graphics2D gfx)
  {
    // Draw pause game screen
    Draw.Sprite(gfx, pause);
  }

  //Pre: None
  //Post: None
  //Desc: Draw endgame screen with final score
  private void DrawEndgame(Graphics2D gfx)
  {
    // Draw endgame screen with total score
    Draw.Sprite(gfx, gameOver);
    Draw.Text(gfx, " " + score, scoreHeader.x - 220, scoreHeader.y + 205, hudFont, Helper.BLACK, 1f);
  }

  //Pre: None
  //Post: None
  //Desc: Draw game statistics with all player data
  private void DrawStats(Graphics2D gfx)
  {
    // Draw the statistics background
    Draw.Sprite(gfx, stats);

    // Number of mosquitoes killed
    Draw.Text(gfx, " " + mosquitoKillCount, 500, 190, statsFont, Helper.BLACK, 1f);

    // Number of laundry the player crashed
    Draw.Text(gfx, " " + laundryKillCount, 500, 260, statsFont, Helper.BLACK, 1f);

    // Number of toy piles the player jumped over
    Draw.Text(gfx, " " + toyKillCount, 500, 340, statsFont, Helper.BLACK, 1f);

    // Number of power-ups collected
    Draw.Text(gfx, " " + powerUps, 500, 410, statsFont, Helper.BLACK, 1f);
  }

  //Pre: None
  //Post: None
  //Desc: Manage screen scrolling for user interface and game graphics
  private void ScrollScreen(SpriteSheet img1, SpriteSheet img2, Vector2F pos1, Vector2F pos2, float deltaTime)
  {
    // Subtract deltaTime for faster obstacle spawning
    scrollScreenTimeLeft = scrollScreenTimeLeft - deltaTime;

    // If increase timer is less than or equal to zero
    if (scrollScreenTimeLeft <= 0)
    {
      // Increase scroll speed by one
      scrollSpeed = scrollSpeed + 0.5f;

      // Reset variable
      scrollScreenTimeLeft = INCREASE_TIME;
    }

    pos1.x = pos1.x + (xDir * scrollSpeed);
    pos2.x = pos2.x + (xDir * scrollSpeed);

    if (pos1.x < -windowWidth)
    {
      pos1.x = pos1.x + (2 * windowWidth);
    }
    else if (pos2.x < -windowWidth)
    {
      pos2.x = pos2.x + (2 * windowWidth);
    }
    else if (pos1.x > windowWidth)
    {
      pos1.x = pos1.x - (2 * windowWidth);
    }
    else if (pos2.x > windowWidth)
    {
      pos2.x = pos2.x - (2 * windowWidth);
    }

    img1.destRec.x = (int) pos1.x;
    img2.destRec.x = (int) pos2.x;
  }

  //Pre: None
  //Post: None
  //Desc: Move game objects suign the true position and speed
  private void MoveGameObjects(SpriteSheet[] objects, Vector2F truePos, Vector2F speed)
  {
    //Add the speed components to the object's true position
    truePos.x = truePos.x + speed.x;
    truePos.y = truePos.y + speed.y;

    //Set all of the object's drawn positions to rounded down true position
    for (SpriteSheet object : objects)
    {
      object.destRec.x = (int) truePos.x;
      object.destRec.y = (int) truePos.y;
    }
  }

  //Pre: None
  //Post: None
  //Desc: MoveGameObjectsTo a certain spot on the game screen
  private void MoveGameObjectsTo(SpriteSheet[] objects, Vector2F truePos, float x, float y)
  {
    //Set the true position to the given point
    truePos.x = x;
    truePos.y = y;

    //Set all of the object's drawn positions to rounded down true position
    for (SpriteSheet object : objects)
    {
      object.destRec.x = (int) truePos.x;
      object.destRec.y = (int) truePos.y;
    }
  }

  //Pre: None
  //Post: None
  //Desc: Handle player collision with the ground and ceiling
  private void PlayerWallCollision()
  {
    //If the player hits the top/bottom walls, pull them in bounds and stop their vertical movement
    if (playerImg[playerState].destRec.y < 0)
    {
      MoveGameObjectsTo(playerImg, playerPos, playerPos.x, 0);
      playerSpeed.y = 0;
    }
    else if (playerImg[playerState].destRec.y + playerImg[playerState].destRec.height >= windowHeight)
    {
      //Readjust the player to be standing directly on the ground
      MoveGameObjectsTo(playerImg, playerPos, playerPos.x, windowHeight - playerImg[playerState].destRec.height);
      playerSpeed.y = 0f;

      //Only switch back to run state if player is landing from a jump
      //As they may already be in the WALK or DUCK states
      if (playerState == JUMP)
      {
        playerState = RUN;
        playerImg[RUN].StartAnimation();
      }

      //The player just landed on the ground
      grounded = true;
    }
    else if (!grounded)
    {
      //The player is off the ground, either jumping or falling
      playerState = JUMP;
      playerImg[RUN].StopAnimation();
      grounded = false;
    }
  }

  //Pre: Objects array is valid
  //Post: Return i value and no object
  //Desc: Find inactive object using an array and for loop
  private int FindInactiveObject(SpriteSheet[] objects)
  {
    // Loop through objects to find inactive objects
    for (int i = 0; i < objects.length; i++)
    {
      // If objects at i aren't visible
      if (!objects[i].GetVisible())
      {
        // Return object at i
        return i;
      }
    }

    // Return no object
    return NO_OBJECT;
  }

  //Pre: None
  //Post: None
  //Desc: Handle object intersection between player and objects
  private void ObjectsIntersect(SpriteSheet[] objects, int state, Vector2F[] position)
  {
    // For the array objects length, work with intersection
    for (int i = 0; i < objects.length; i++)
    {
      // If the object at i is visible
      if (objects[i].GetVisible())
      {
        // If an intersection between the object and player state occurs
        if (Helper.Intersects(objects[i].destRec, playerImg[state].destRec))
        {
          // Set object visibility to false
          objects[i].SetVisible(false);

          // Reduce health by one
          health = health - 1;

          // Reset object to a new place at edge of screen
          objects[i].destRec.x = windowWidth;
          objects[i].destRec.y = (int) Helper.RandomValue(50, 150);
          position[i].x = objects[i].destRec.x;
          position[i].y = objects[i].destRec.y;
        }
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Handle object intersection during invincibility power-up, no health is taken away
  private void ObjectsIntersectNoDamage(SpriteSheet[] objects, Vector2F[] position)
  {
    // For the array object length, work with intersection
    for (int i = 0; i < objects.length; i++)
    {
      // If the objects at i is visible
      if (objects[i].GetVisible())
      {
        // And intersection occurs between the object and player state occurs
        if (Helper.Intersects(objects[i].destRec, playerImg[0].destRec))
        {
          // Set object to transparent
          objects[i].SetVisible(false);

          // Reset object to edge of screen
          objects[i].destRec.x = windowWidth;
          objects[i].destRec.y = (int) Helper.RandomValue(50, 150);
          position[i].x = objects[i].destRec.x;
          position[i].y = objects[i].destRec.y;
        }
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Deactivate object at i from an array of objects
  private void DeactivateObject(SpriteSheet[] objects)
  {
    // Use for loop to deactivate the obstacles if it hits edge of screen
    for (int i = 0; i < objects.length; i++)
    {
      // If object at i are visible
      if (objects[i].GetVisible())
      {
        // If object at i reached edge of screen
        if (objects[i].destRec.x <= 0)
        {
          // Deactivate the obstacle
          objects[i].SetVisible(false);

          // Award player 10 points
          IncreaseBy(10);

          // If objects at i is equal to toys at i
          if (objects[i] == toys[i])
          {
            // Add one to the kill count
            toyKillCount = toyKillCount + 1;
          }
        }
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Move all game obstacles, power-ups and power-downs
  private void MoveObjects(SpriteSheet[] objects, Vector2F[] position, float speed)
  {
    // Use for loop to move toy obstacles
    for (int i = 0; i < objects.length; i++)
    {
      // If toy at i is visible
      if (objects[i].GetVisible())
      {
        // Move toy position and destRec using the speed
        position[i].x = objects[i].destRec.x + -speed;
        objects[i].destRec.x = (int) position[i].x;
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Spawn obstacles using spawn index and a random generated chance variable
  private void SpawnObjects()
  {
    // Spawn index variable
    int spawnId;

    // Generate a new number between 4 and 6
    chance = (int) Helper.RandomValue(1, 4);

    // If the random number generated is 1, spawn a mosquito
    if (chance == MOSQUITO)
    {
      // Find an inactive mosquito object in the array of this type of obstacles
      spawnId = FindInactiveObject(mosquitos);

      // If the spawn index is not -1, spawn mosquito
      if (spawnId != NO_OBJECT)
      {
        // Set mosquito at spawn index to visible
        mosquitos[spawnId].SetVisible(true);

        // Set mosquito x and y coordinates
        mosquitos[spawnId].destRec.x = 850;
        mosquitos[spawnId].destRec.y = (int) Helper.RandomValue(50, 100);

        // Set true mosquito position x and y coordinates
        mosquitoPos[spawnId].x = mosquitos[spawnId].destRec.x;
        mosquitoPos[spawnId].y = mosquitos[spawnId].destRec.y;
      }
    }

    // If the random number generated is 2, spawn a laundry obstacle
    if (chance == LAUNDRY)
    {
      // Find an inactive laundry object in the array of this type of obstacles
      spawnId = FindInactiveObject(laundry);

      // If the spawn index is not -1, spawn laundry obstacle
      if (spawnId != NO_OBJECT)
      {
        // Set laundry obstacle at spawn index to visible
        laundry[spawnId].SetVisible(true);

        // Set laundry x and y coordinates
        laundry[spawnId].destRec.x = 850;
        laundry[spawnId].destRec.y = 270;

        // Set true laundry position x and y coordinates
        laundryPos[spawnId].x = laundry[spawnId].destRec.x;
        laundryPos[spawnId].y = laundry[spawnId].destRec.y;
      }
    }

    // If the random number generated is 3, spawn a toy obstacle
    if (chance == TOYS)
    {
      // Find an inactive toy object in the array of this type of obstacles
      spawnId = FindInactiveObject(toys);

      // If the spawn index is not -1, spawn toy obstacle
      if (spawnId != NO_OBJECT)
      {
        // Set toy obstacle at spawn index to visible
        toys[spawnId].SetVisible(true);

        // Set toy x and y coordinates
        toys[spawnId].destRec.x = 850;
        toys[spawnId].destRec.y = 380;

        // Set true toy position x and y coordinates
        toysPos[spawnId].x = toys[spawnId].destRec.x;
        toysPos[spawnId].y = toys[spawnId].destRec.y;
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Spawn power-ups using a spawn index and a random generated chance variable
  private void SpawnPowerUps()
  {
    // Spawn index variable
    int spawnId;

    // Generate a random variable from 4 to 6
    chance1 = (int) Helper.RandomValue(4, 7);

    // If the random number generated is 4, spawn a coffee
    if (chance1 == COFFEE)
    {
      // Find an inactive coffee object in the array of this type of obstacles
      spawnId = FindInactiveObject(coffee);

      // If the spawn index is not -1, spawn coffee powerup
      if (spawnId != NO_OBJECT)
      {
        // Set coffee visibility to true
        coffee[spawnId].SetVisible(true);

        // Move coffee to edge of the screen
        coffee[spawnId].destRec.x = 850;
        coffee[spawnId].destRec.y = 50;
        coffeePos[spawnId].x = mosquitos[spawnId].destRec.x;
        coffeePos[spawnId].y = mosquitos[spawnId].destRec.y;
        IncreaseBy(10);
      }
    }

    // If the random number generated is 5, spawn a pizza
    if (chance1 == PIZZA)
    {
      // Find an inactive pizza object in the array of this type of obstacles
      spawnId = FindInactiveObject(pizza);

      // If the spawn index is not -1 and score is greater than 50, spawn pizza power-down
      if (spawnId != NO_OBJECT && score >= 50)
      {
        // Set visibility to true
        pizza[spawnId].SetVisible(true);

        // Move pizza object
        pizza[spawnId].destRec.x = 850;
        pizza[spawnId].destRec.y = 50;
        pizzaPos[spawnId].x = pizza[spawnId].destRec.x;
        pizzaPos[spawnId].y = pizza[spawnId].destRec.y;
      }
    }

    // If the random number generated is 6, spawn a water
    if (chance1 == WATER)
    {
      // Find an inactive water object in the array of this type of obstacles
      spawnId = FindInactiveObject(water);

      // If the spawn index is not -1, spawn water power-up
      if (spawnId != NO_OBJECT && health < 3)
      {
        // Set visibility to true
        water[spawnId].SetVisible(true);

        // Move water object to edge of screen
        water[spawnId].destRec.x = 850;
        water[spawnId].destRec.y = 50;
        waterPos[spawnId].x = water[spawnId].destRec.x;
        waterPos[spawnId].y = water[spawnId].destRec.y;
      }
    }
  }

  //Pre: None
  //Post: None
  //Desc: Method to quickly and efficiently increase the score by needed number
  private void IncreaseBy(int increase)
  {
    // Increase score by increase number
    score = score + increase;

    // If the score is greater than high score
    if (score > highScore)
    {
      // High score equals to score
      highScore = score;
    }
  }
}