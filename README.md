Hello Everyone! This is my first real released project(other than some random minecraft datapacks)
I plan on developing this to be able to do more ds/3ds games in the future as I find interest, and maybe even supporting streaming from a modded 3ds!
Right now, all this supports is the ultra wormhole hunts, but I'll probably try to get a type null mode soonish.

With that out of the way,
# Installation
## **Requirements:**
- Right now, I've only tested this with [lime3ds](https://github.com/Lime3DS/lime3ds-archive)(now [Azahar](https://github.com/azahar-emu/azahar?tab=readme-ov-file), but I didn't test this one), but it should be the exact same with any other emulators as long as they allow you to set keybinds.
- [vJoy](https://sourceforge.net/projects/vjoystick/) is what allows for controller inputs, which allows for the resets to continue even if the emulator isn't the selected screen(still must be visable though)
- [Java](https://www.java.com/en/download/) is needed to actually run the .jar file. For help with install, I recommend [this video](https://www.youtube.com/watch?v=jPwrWjEwtrw) if you need it
- A save file that is set up right in front of the 

## **Process:**
- Make sure you have all of the requirements installed
- Download the [latest release](https://github.com/DR4CONS/USUMShinyBot/releases)
- Run the ShinyBot-x.x.jar file once to create the config folder, and a second time to bring up the GUI and open your emulator.
- In the emulator, start an encounter, and when the bottom screen first lights up, press the "Set Color" button to the right of the Encounter RGB fields.
- Then, when your bottom screen "unlocks"(it becomes brighter and gives options for battle, bag, run etc), press the "Set Color" button, this time to the right of the Battle RGB fields.
- Next, go to the emulator's controls settings and press "Controller" on the GUI.
- Ready the emulator to record each keybind(this is done on lime3ds by clicking on the current bind), then press the corrosponding button on the "Controller Inputs" popup
- Finally, in the "Screen" dropdown at the top right of the GUI, select the name of the window that displays the emulated game, example: "Lime3ds 2116 | Pokemon Ultra Moon | Primary Window. If your emulator screen doesn't show up, select a random one and check again. Selecing a screen will refresh the dropdown.
- Now pressing "Start" should start the hunt! If you want to set up discord notifications, go to [Setting up discord notifications](#setting-up-discord-notifications)

## Other Config Settings
- **Mode**: Select the hunt you are set up for, this determines the reset and encounter steps.
- **Screen Layout**: *default* is for if you have one window with both screens in the same places they are on a DS. *bottom_screen* is for if you have both screens in seperate windows. For this, you would select the bottom screen in the "Screen" dropdown
- **Game Speed**: If you want to run your emulator at a higher game speed, set your speed as a percentage here. **WARNING**: Higher game speed will lead to false positives with smaller ammounts of lag
- **Minimum Delay**: I recommend you keep this at default. The delay caused by a shiny pokemon is ~1.2 seconds, but if you keep it too close to that number, even a little lag can cause false positives. Having this set too low can cause the same thing.
- **Has Shiny Charm**: I mean, this one's pretty self-explanatory... It's used in the odds calculations.

## Setting up discord notifications
There are 2 checkboxes you probably haven't checked yet: Save Pictures, and Listen for instructions. Go ahead and check Listen for instructions and it should automatically enable the first checkbox. To download the discord bot, go to [its repository](https://github.com/DR4CONS/ShinyHuntDiscordBot) and follow the instructions there.
**NOTE:** The catch option will throw **ONE** ball and then save. If you plan on using this, either have master ball selected or consider using the following cheat codes.
<details>
  <summary>**VIEW CODES**</summary>
  Make sure you are using the correct version!
  
  [100%Catch v1.2]
```
  005BBEA0 E5D00008
  005BBEA4 E92D4003
  005BBEA8 E59D0010
  005BBEAC E59F100C
  005BBEB0 E1510000
  005BBEB4 024000F8
  005BBEB8 058D0010
  005BBEBC E8BD8003
  005BBEC0 0070C62C
  004ACD2C EB043C5B
```

  [100%Catch v1.1]
```
005BBEA0 E5D00008
005BBEA4 E92D4003
005BBEA8 E59D0010
005BBEAC E59F100C
005BBEB0 E1510000
005BBEB4 024000F8
005BBEB8 058D0010
005BBEBC E8BD8003
005BBEC0 0070C62C
004ACD24 EB043C5D
```
  [100%Catch v1.0]
```
58036714 0A000004
50667004 00000000
00667004 00000010
D0000000 00000000
D0000000 00000000
60667004 00000000
D9000000 00667004
D4000000 FFFFFFFF
D6000000 00667004
D0000000 00000000
D3000000 00000000
50667004 00000001
08036714 EA000004
D0000000 00000000
```
</details>

# Other things to be aware of
As I've mentioned before, lag can lead to false positives, but shouldn't if you don't tweak the default settings too much. If your game is not noticably lagging, but your program is giving false positives, contact me through my discord support server at the bottom of this page.

**This program does support parallel hunting**, but there is a little more setup required for it. To do this, after you have vJoy set up, if you open "Configure vJoy" from your start menu, you can add controllers there. There is only one controller by default, so this is required. When setting up multiple controllers, you must also raise the number of buttons on each successive controller by 5(controller 1: 5 buttons, controller 2: 10 buttons ...). This is because if you have 2 controllers using buttons 1-5, when the first controller resets one of your emulators, it will also reset the second one. If this happens, even if one emulator gets a shiny, the other one will reset it.
Another thing to note about parallel hunting is that multiple instances of the same save file, when reset at the same time, will get the same pokemon. So make sure that you at least give a little delay between starting two emulators running the same save file.



# Help
**If you are unable to open the .jar file**, try opening a command prompt in that folder and run "java -jar ShinyBot-x.x.jar" (make sure you replace "x.x" with the correct version)
Please use the [Issues Page](https://github.com/DR4CONS/USUMShinyBot/issues/new?template=Blank+issue), or my [discord help server](http://discord.gg/BqkaSa82h5) if you have any problems.
