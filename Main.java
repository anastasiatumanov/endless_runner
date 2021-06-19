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
  //Required Basic Game Functional Data
  private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

  private static int screenWidth = 800;
  private static int screenHeight = 480;
  //private static int screenWidth = device.getDisplayMode().getWidth() / 3;
  //private static int screenHeight = device.getDisplayMode().getHeight() / 3;
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
  private static String gameName = "Just Another Day";
  private static int fps = 60;

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

  // Create font for the timer on screen and statistics
  Font hudFont = new Font("Arial", Font.BOLD + Font.ITALIC, 40);
  Font statsFont = new Font("Arial", Font.BOLD + Font.ITALIC, 30);

  // Create Vector2F positions for the text during play
  Vector2F secondClockLoc = new Vector2F(0, 40);
  Vector2F scoreHeader = new Vector2F(550, 40);


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

  // Point awarded every second to the score
  final float AWARD_POINT = 1000; // ms
  float awardTimeLeft = AWARD_POINT;

  // Chance variables for the random number generators
  float chance;
  float chance1;

  // Mosquito obstacle array
  SpriteSheet[] mosquitos = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] mosquitoPos = new Vector2F[MAX_OBSTACLE];
  float mosquitoSpeed = 4.5f;

  // Laundry obstacle array
  SpriteSheet[] laundry = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] laundryPos = new Vector2F[MAX_OBSTACLE];
  float laundrySpeed = 4.2f;

  // Toys obstacle array
  SpriteSheet[] toys = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] toysPos = new Vector2F[MAX_OBSTACLE];
  float toysSpeed = 4.2f;

  // Variables for the different player states
  final int RUN = 0;
  final int JUMP = 1;
  final int PUNCH = 2;

  // Store gravity as a float
  final float GRAVITY = 9f / fps;

  // Scroll screen variable direction
  final int LEFT = 1;
  final int RIGHT = -1;
  final int STOP = 0;

  // Create SpriteSheet array for character actions/movements
  SpriteSheet[] playerImg = new SpriteSheet[3];

  // Create position for player
  Vector2F playerPos;

  // Position for player speed
  Vector2F playerSpeed = new Vector2F(0f, 0f);

  // Forces variable position
  Vector2F forces = new Vector2F(0, GRAVITY);

  // Jumpspeed value
  float jumpSpeed = -6.5f;

  // Boolean to check whether character is grounded
  boolean grounded = false;

  // Current playerState
  int playerState = RUN;

  // Sprite sheets for the different states of player
  SpriteSheet runImg;
  SpriteSheet jumpImg;
  SpriteSheet punchImg;

  // Spritesheets for coffee power up
  SpriteSheet[] coffee = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] coffeePos = new Vector2F[MAX_OBSTACLE];
  float coffeeSpeed = 4.2f;

  // Spritesheets for pizza power down
  SpriteSheet[] pizza = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] pizzaPos = new Vector2F[MAX_OBSTACLE];
  float pizzaSpeed = 4.2f;

  // Spritesheets for water power up
  SpriteSheet[] water = new SpriteSheet[MAX_OBSTACLE];
  Vector2F[] waterPos = new Vector2F[MAX_OBSTACLE];
  float waterSpeed = 4.2f;

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

  // Endgame screen
  SpriteSheet endGame;

  // Pause screen game
  SpriteSheet pause;

  // Stats game screen
  SpriteSheet stats;

  // Setting variable for scrolling screen
  int xDir = STOP;

  // Variable for background scroll speed
  float scrollSpeed = 4f;

  // Userscore variable tracks points collected by player
  int score = 0;

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
    /***********************************************************************
     DO NOT TOUCH THIS SECTION
     ***********************************************************************/
    windowWidth = screenWidth;
    windowHeight = screenHeight - TITLE_BAR_HEIGHT;

    GameContainer gameContainer = new GameContainer(new Main(), gameName, screenWidth, screenHeight, fps);
    gameContainer.Start();
  }

  public void LoadContent(GameContainer gc)
  {
    //TODO: This subprogram automatically happens only once, just before the actual game loop starts.

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
      rollingPins[i].destRec = new Rectangle(0, 0, (int) (rollingPins[i].GetFrameWidth() * 0.2), (int) (rollingPins[i].GetFrameHeight() * 0.2));
      rollingPins[i].SetVisible(false);
      pinPos[i] = new Vector2F(rollingPins[i].destRec.x, rollingPins[i].destRec.y);
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

  private void SetUpGame()
  {
    System.out.println("Set up game");
    // initialize starting variables
    mosquitoSpeed = 4.5f;
    laundrySpeed = 4.5f;
    toysSpeed = 4.5f;

    score = 0;
    health = 3;
    clockTimer = 0;

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

    for (int i = 0; i < mosquitos.length; i++)
    {
      mosquitos[i].destRec = new Rectangle(700, 150, (int) (mosquitos[i].GetFrameWidth() * 0.2), (int) (mosquitos[i].GetFrameHeight() * 0.2));
      mosquitos[i].SetVisible(false);
      mosquitoPos[i] = new Vector2F(mosquitos[i].destRec.x, mosquitos[i].destRec.y);
    }

    for (int i = 0; i < laundry.length; i++)
    {
      laundry[i].destRec = new Rectangle(700, 250, (int) (laundry[i].GetFrameWidth() * 0.6), (int) (laundry[i].GetFrameHeight() * 0.6));
      laundry[i].SetVisible(false);
      laundryPos[i] = new Vector2F(laundry[i].destRec.x, laundry[i].destRec.y);
    }

    for (int i = 0; i < toys.length; i++)
    {
      toys[i].destRec = new Rectangle(700, 320, (int) (toys[i].GetFrameWidth() * 0.15), (int) (toys[i].GetFrameHeight() * 0.15));
      toys[i].SetVisible(false);
      toysPos[i] = new Vector2F(toys[i].destRec.x, toys[i].destRec.y);
    }

    for (int i = 0; i < coffee.length; i++)
    {
      coffee[i].destRec = new Rectangle(700, 150, (int) (coffee[i].GetFrameWidth() * 0.3), (int) (coffee[i].GetFrameHeight() * 0.3));
      coffee[i].SetVisible(false);
      coffeePos[i] = new Vector2F(coffee[i].destRec.x, coffee[i].destRec.y);
    }

    for (int i = 0; i < pizza.length; i++)
    {
      pizza[i].destRec = new Rectangle(700, 150, (int) (pizza[i].GetFrameWidth() * 0.3), (int) (pizza[i].GetFrameHeight() * 0.3));
      pizza[i].SetVisible(false);
      pizzaPos[i] = new Vector2F(pizza[i].destRec.x, pizza[i].destRec.y);
    }

    for (int i = 0; i < water.length; i++)
    {
      water[i].destRec = new Rectangle(700, 150, (int) (water[i].GetFrameWidth() * 0.3), (int) (water[i].GetFrameHeight() * 0.3));
      water[i].SetVisible(false);
      waterPos[i] = new Vector2F(water[i].destRec.x, water[i].destRec.y);
    }

    timerBarBase = new GameRectangle(windowWidth/4 - barWidth/2, 120, barWidth, 20,2, Helper.WHITE, Helper.DARK_GRAY, 0f);
    timerBarCurrent = new GameRectangle(windowWidth/4 - barWidth/2, 120, barWidth, 20, 2, Helper.WHITE, Helper.BLUE, 0f);
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

  private void ChangeState(int newState) {
    System.out.println("Change state from " + gameState + " to " + newState);
    if (newState == gameState)
    {
      System.out.println("Warning: state is already " + newState);
      return;
    }
    gameState = newState;
  }

  private void UpdateInstructions()
  {
    if (Input.IsKeyDown(KeyEvent.VK_ENTER))
    {
      // If user presses ENTER, the game begins
      ChangeState(GAMEPLAY);
    }
  }

  private void UpdateGamePlay(float deltaTime)
  {
    // Handle user input
    HandleUserInput();

    // Screen scrolls to the right
    xDir = RIGHT;

    //Continue tracking total time passed
    clockTimer += deltaTime;

    //Continue to reduce repeating timer
    repeatingTimer -= deltaTime;
    repeatingTimer = REPEAT_TIME;

    // Use method for scrolling screen
    ScrollScreen(bg1, bg2, bg1Pos, bg2Pos, scrollSpeed);

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
    MoveObjects(mosquitos, mosquitoPos, mosquitoSpeed);
    MoveObjects(laundry, laundryPos, laundrySpeed);
    MoveObjects(toys, toysPos, toysSpeed);

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
      // Method which handles intersection and damage given
      ObjectsIntersect(laundry, RUN, laundryPos);
      ObjectsIntersect(toys, RUN, toysPos);
    }

    // If the player state is jump and player jumps over laundry, one heart is lost
    if (playerState == JUMP && activatedTimer <= 0)
    {
      // Method which handles intersection and damage given
      ObjectsIntersect(laundry, JUMP, laundryPos);
    }

    // Reduce active ability timer if it is active
    if (activatedTimer > 0)
    {
      activatedTimer -= deltaTime;
    }

    for (int i = 0; i < laundry.length; i++)
    {
      if (laundry[i].GetVisible())
      {
        if (playerState == PUNCH)
        {
          if (Helper.Intersects(laundry[i].destRec, playerImg[PUNCH].destRec))
          {
            laundry[i].SetVisible(false);
            score = score + 10;
            laundry[i].destRec.x = windowWidth;
            laundry[i].destRec.y = 270;
            laundryPos[i].x = laundry[i].destRec.x;
            laundryPos[i].y = laundry[i].destRec.y;
            laundryKillCount = laundryKillCount + 1;
            System.out.println("Laundry Kill Count = " + laundryKillCount);
          }
        }
      }
    }

    for (int i = 0; i < rollingPins.length; i++)
    {
      if (rollingPins[i].GetVisible())
      {
        if (mosquitos[i].GetVisible())
        {
          if (Helper.Intersects(mosquitos[i].destRec, rollingPins[i].destRec))
          {
            mosquitos[i].SetVisible(false);
            mosquitos[i].destRec.x = windowWidth;
            mosquitos[i].destRec.y = (int) Helper.RandomValue(50, 150);
            mosquitoPos[i].x = mosquitos[i].destRec.x;
            mosquitoPos[i].y = mosquitos[i].destRec.y;
            rollingPins[i].SetVisible(false);
            score = score + 10;
            mosquitoKillCount = mosquitoKillCount + 1;
            System.out.println("Mosquito Kill Count = " + mosquitoKillCount);
            System.out.println("Intersection occured");
          }
        }
      }
    }

    if (health == 0)
    {
      System.out.println("Health is zero, end game");
      ChangeState(ENDGAME);
    }

    increaseTimeLeft = increaseTimeLeft - deltaTime;
    if (increaseTimeLeft <= 0)
    {
      increaseTimeLeft = INCREASE_TIME;
      toysSpeed = toysSpeed + 1;
      laundrySpeed = laundrySpeed + 1;
      mosquitoSpeed = mosquitoSpeed + 1;
    }

    awardTimeLeft = awardTimeLeft - deltaTime;
    if (awardTimeLeft <= 0)
    {
      awardTimeLeft = AWARD_POINT;
      score = score + 1;
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
    MoveObjects(coffee, coffeePos, coffeeSpeed);
    MoveObjects(pizza, pizzaPos, pizzaSpeed);
    MoveObjects(water, waterPos, waterSpeed);

    // Deactivate objects when they touch the edge of the screen
    DeactivateObject(coffee);
    DeactivateObject(water);
    DeactivateObject(pizza);

    for (int i = 0; i < coffee.length; i++)
    {
      if (coffee[i].GetVisible())
      {
        if (playerState == JUMP)
        {
          if (Helper.Intersects(coffee[i].destRec, playerImg[JUMP].destRec) && activatedTimer <= 0)
          {
            coffee[i].SetVisible(false);
            coffee[i].destRec.x = windowWidth;
            coffee[i].destRec.y = (int) Helper.RandomValue(50, 150);
            coffeePos[i].x = coffee[i].destRec.x;
            coffeePos[i].y = coffee[i].destRec.y;
            powerUps = powerUps + 1;

            ObjectsIntersectNoDamage(laundry, RUN, laundryPos);
            ObjectsIntersectNoDamage(mosquitos, RUN, mosquitoPos);
            ObjectsIntersectNoDamage(toys, RUN, toysPos);
            activatedTimer = ACTIVE_TIME;
            timerBarBase.SetTransparency(1f);
            timerBarCurrent.SetTransparency(1f);
          }
        }
      }
    }

    if (activatedTimer <= 0)
    {
      timerBarBase.SetTransparency(0f);
      timerBarCurrent.SetTransparency(0f);
    }

    timerBarCurrent.SetWidth(activatedTimer/ACTIVE_TIME * barWidth);

    for (int i = 0; i < pizza.length; i++)
    {
      if (pizza[i].GetVisible())
      {
        if (playerState == JUMP)
        {
          // running backwards
          if (Helper.Intersects(pizza[i].destRec, playerImg[JUMP].destRec))
          {
            if (score > 50)
            {
              score = score - 50;
            }
            pizza[i].SetVisible(false);
            pizza[i].destRec.x = windowWidth;
            pizza[i].destRec.y = (int) Helper.RandomValue(50, 150);
            pizzaPos[i].x = pizza[i].destRec.x;
            pizzaPos[i].y = pizza[i].destRec.y;
          }
        }
      }
    }

    for (int i = 0; i < water.length; i++)
    {
      if (water[i].GetVisible())
      {
        if (playerState == JUMP)
        {
          if (Helper.Intersects(water[i].destRec, playerImg[JUMP].destRec))
          {
            water[i].SetVisible(false);
            health = 3;
            water[i].destRec.x = windowWidth;
            water[i].destRec.y = (int) Helper.RandomValue(50, 150);
            waterPos[i].x = water[i].destRec.x;
            waterPos[i].y = water[i].destRec.y;
            powerUps = powerUps + 1;
          }
        }
      }
    }
  }

  private void HandleUserInput()
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
    if (Input.IsKeyPressed(KeyEvent.VK_H) && grounded)
    {
      // Change player state to punch
      playerState = PUNCH;

      // Stop running animation
      playerImg[RUN].StopAnimation();
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

  private void UpdateEndgame()
  {
    // Change gamestates based on user input
    if (Input.IsKeyDown(KeyEvent.VK_ENTER))
    {
      ChangeState(MENU);
      SetUpGame();
      return;
    }
    if(Input.IsKeyDown(KeyEvent.VK_S))
    {
      ChangeState(STATS);
    }
  }

  private void UpdateStats()
  {
    if (Input.IsKeyDown(KeyEvent.VK_M))
    {
      ChangeState(MENU);
      SetUpGame();
    }
  }

  private void DrawMenu(Graphics2D gfx)
  {
    // Draw intro sprite animation
    Draw.Sprite(gfx, intro);

    // If intro stops animating, show static menu screen
    if (intro.IsAnimating())
    {

    }
    else
    {
      // Draw menu screen
      Draw.Sprite(gfx, menuImg);
    }
  }

  private void DrawInstructions(Graphics2D gfx)
  {
    // Draw game instructions
    Draw.Sprite(gfx, instructions);
  }

  private void DrawGamePlay(Graphics2D gfx)
  {
    // Draw both backgrounds for scrolling screen
    Draw.Sprite(gfx, bg1);
    Draw.Sprite(gfx, bg2);

    // Draw player at current player state
    Draw.Sprite(gfx, playerImg[playerState]);
    SpriteSheet s = playerImg[playerState];
    // Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.destRec.width, s.destRec.height, 3, Color.RED, 1f);

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
    for (int i = 0; i < rollingPins.length; i++)
    {
      Draw.Sprite(gfx, rollingPins[i]);
    }

    // Draw mosquitoes for obstacle
    for (int i = 0; i < mosquitos.length; i++)
    {
      if (mosquitos[i].GetVisible())
      {
        s = mosquitos[i];
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.GetFrameWidth(), s.GetFrameHeight(), 3, Color.CYAN, 1f);
        Draw.Sprite(gfx, mosquitos[i]);
      }
    }

    // Draw laundry piles for obstacles
    for (int i = 0; i < laundry.length; i++)
    {
      if (laundry[i].GetVisible())
      {
        s = laundry[i];
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.destRec.width, s.destRec.height, 3, Color.ORANGE, 1f);
        Draw.Sprite(gfx, laundry[i]);
      }
    }

    // Draw toy piles for obstacles
    for (int i = 0; i < toys.length; i++)
    {
      if (toys[i].GetVisible())
      {
        s = toys[i];
        // Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.destRec.width, s.destRec.height, 3, Color.BLACK, 1f);
        Draw.Sprite(gfx, toys[i]);
      }
    }

    // Draw coffee cups for power-ups
    for (int i = 0; i < coffee.length; i++)
    {
      if (coffee[i].GetVisible())
      {
        s = coffee[i];
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.GetFrameWidth(), s.GetFrameHeight(), 3, Color.CYAN, 1f);
        Draw.Sprite(gfx, coffee[i]);
      }
    }

    // Draw pizza for power-downs
    for (int i = 0; i < pizza.length; i++)
    {
      if (pizza[i].GetVisible())
      {
        s = pizza[i];
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.destRec.width, s.destRec.height, 3, Color.ORANGE, 1f);
        Draw.Sprite(gfx, pizza[i]);
      }
    }

    // Draw water glasses for power-ups
    for (int i = 0; i < water.length; i++)
    {
      if (water[i].GetVisible())
      {
        s = water[i];
        //Draw.Rect(gfx, s.destRec.x, s.destRec.y, s.destRec.width, s.destRec.height, 3, Color.BLACK, 1f);
        Draw.Sprite(gfx, water[i]);
      }
    }

    //Display the total time passed in full seconds
    Draw.Text(gfx, "Time: " + (int) (clockTimer / SECOND) + "s", secondClockLoc.x, secondClockLoc.y, hudFont, Helper.BLUE, 1f);

    // Display user score
    Draw.Text(gfx, "Score: " + score, scoreHeader.x, scoreHeader.y, hudFont, Helper.BLUE, 1f);

    timerBarBase.Draw(gfx);
    timerBarCurrent.Draw(gfx);
  }

  private void DrawPause(Graphics2D gfx)
  {
    // Draw pause game screen
    Draw.Sprite(gfx, pause);
  }

  private void DrawEndgame(Graphics2D gfx)
  {
    // Draw endgame screen with total score
    Draw.Sprite(gfx, gameOver);
    Draw.Text(gfx, " " + score, scoreHeader.x - 220, scoreHeader.y + 205, hudFont, Helper.BLACK, 1f);
  }

  private void DrawStats(Graphics2D gfx)
  {
    Draw.Sprite(gfx, stats);
    Draw.Text(gfx, " " + mosquitoKillCount, 500, 190, statsFont, Helper.BLACK, 1f);
    Draw.Text(gfx, " " + laundryKillCount, 500, 260, statsFont, Helper.BLACK, 1f);
    Draw.Text(gfx, " " + toyKillCount, 500, 340, statsFont, Helper.BLACK, 1f);
    Draw.Text(gfx, " " + powerUps, 500, 410, statsFont, Helper.BLACK, 1f);
  }

  private void ScrollScreen(SpriteSheet img1, SpriteSheet img2, Vector2F pos1, Vector2F pos2, float speed)
  {
    pos1.x = pos1.x + (xDir * speed);
    pos2.x = pos2.x + (xDir * speed);

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

  private void MoveGameObject(SpriteSheet object, Vector2F truePos, Vector2F speed)
  {
    //Add the speed components to the object's true position
    truePos.x = truePos.x + speed.x;
    truePos.y = truePos.y + speed.y;

    //Set the object's drawn position to rounded down true position
    object.destRec.x = (int) truePos.x;
    object.destRec.y = (int) truePos.y;
  }

  private void MoveGameObjects(SpriteSheet[] objects, Vector2F truePos, Vector2F speed)
  {
    //Add the speed components to the object's true position
    truePos.x = truePos.x + speed.x;
    truePos.y = truePos.y + speed.y;

    //Set all of the object's drawn positions to rounded down true position
    for (int i = 0; i < objects.length; i++)
    {
      objects[i].destRec.x = (int) truePos.x;
      objects[i].destRec.y = (int) truePos.y;
    }
  }

  private void MoveGameObjectsTo(SpriteSheet[] objects, Vector2F truePos, float x, float y)
  {
    //Set the true position to the given point
    truePos.x = x;
    truePos.y = y;

    //Set all of the object's drawn positions to rounded down true position
    for (int i = 0; i < objects.length; i++)
    {
      objects[i].destRec.x = (int) truePos.x;
      objects[i].destRec.y = (int) truePos.y;
    }
  }

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
    else if (grounded == false)
    {
      ;
      //The player is off the ground, either jumping or falling
      playerState = JUMP;
      playerImg[RUN].StopAnimation();
      grounded = false;
    }
  }

  private int FindInactiveObject(SpriteSheet[] objects)
  {
    for (int i = 0; i < objects.length; i++)
    {
      if (!objects[i].GetVisible())
      {
        return i;
      }
    }

    return NO_OBJECT;
  }

  private void ObjectsIntersect(SpriteSheet[] objects, int state, Vector2F[] position)
  {
    for (int i = 0; i < objects.length; i++)
    {
      if (objects[i].GetVisible())
      {
        if (Helper.Intersects(objects[i].destRec, playerImg[state].destRec))
        {
          objects[i].SetVisible(false);
          health = health - 1;
          objects[i].destRec.x = windowWidth;
          objects[i].destRec.y = (int) Helper.RandomValue(50, 150);
          position[i].x = objects[i].destRec.x;
          position[i].y = objects[i].destRec.y;
        }
      }
    }
  }

  private void ObjectsIntersectNoDamage(SpriteSheet[] objects, int state, Vector2F[] position)
  {
    for (int i = 0; i < objects.length; i++)
    {
      if (objects[i].GetVisible())
      {
        if (Helper.Intersects(objects[i].destRec, playerImg[state].destRec))
        {
          objects[i].SetVisible(false);
          objects[i].destRec.x = windowWidth;
          objects[i].destRec.y = (int) Helper.RandomValue(50, 150);
          position[i].x = objects[i].destRec.x;
          position[i].y = objects[i].destRec.y;
        }
      }
    }
  }

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
          score = score + 10;

          if (objects[i] == toys[i])
          {
            toyKillCount = toyKillCount + 1;
            System.out.println("Toy Kill Count = " + toyKillCount);
          }
        }
      }
    }
  }

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

  private void SpawnObjects()
  {
    int spawnId = NO_OBJECT;

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

  private void SpawnPowerUps()
  {
    int spawnId = NO_OBJECT;

    chance1 = (int) Helper.RandomValue(4, 7);

    // If the random number generated is 4, spawn a coffee
    if (chance1 == COFFEE)
    {
      // Find an inactive coffee object in the array of this type of obstacles
      spawnId = FindInactiveObject(coffee);

      // If the spawn index is not -1, spawn coffee powerup
      if (spawnId != NO_OBJECT)
      {
        coffee[spawnId].SetVisible(true);
        coffee[spawnId].destRec.x = 850;
        coffee[spawnId].destRec.y = 50;
        coffeePos[spawnId].x = mosquitos[spawnId].destRec.x;
        coffeePos[spawnId].y = mosquitos[spawnId].destRec.y;
      }
      else
      {
        System.out.println("No more inactive objects left : coffee");
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
        pizza[spawnId].SetVisible(true);
        pizza[spawnId].destRec.x = 850;
        pizza[spawnId].destRec.y = 50;
        pizzaPos[spawnId].x = pizza[spawnId].destRec.x;
        pizzaPos[spawnId].y = pizza[spawnId].destRec.y;
      }
      else
      {
        System.out.println("No more inactive objects left : pizza");
      }
    }

    // If the random number generated is 6, spawn a water
    if (chance1 == WATER)
    {
      // Find an inactive water object in the array of this type of obstacles
      spawnId = FindInactiveObject(water);

      // If the spawn index is not -1, spawn water power-up
      if (spawnId != NO_OBJECT)
      {
        water[spawnId].SetVisible(true);
        water[spawnId].destRec.x = 850;
        water[spawnId].destRec.y = 50;
        waterPos[spawnId].x = water[spawnId].destRec.x;
        waterPos[spawnId].y = water[spawnId].destRec.y;
      }
      else
      {
        System.out.println("No more inactive objects left : water");
      }
    }
  }
}