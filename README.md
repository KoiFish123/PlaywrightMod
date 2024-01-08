# Playwright Mod

This is my mod. It will be having more than one character (dubbed PlaywrightCharacter), each having a different play style, but all will be having some pretty similar mechanics.

I will be going over the common mechanics, and then I will be talking more about each character.

## Common Mechanic:

---
### EX Charges and EX Gauge


Each character have an EX gauge on top of their head. The gauge fills as the player lands attacks and gaining EX Charges, but drains when the player takes damage. Ending a turn without filling the gauge will also drain it.

Depends on the character and its stance (if any), the more EX charges you have the more power you get. Taking "The Crownsguard" as an example, which gain more Strength while in his Bruiser stance the more EX Charges he has.

Beside, you will need the EX Charge if you want to play EX Actions or cards that need it. Note that under normal circumstances, the EX Gauge will not be carried over from previous fight.

---
### EX Actions

When in combat, the player may initiate EX Actions under certain conditions to unleash brutal attacks on their enemies.

You will need to spend some EX Charge to play EX Actions, and dealing damage via EX Actions will not fill the EX Gauge.

Furthermore, the criteria for EX Actions can contextually vary depending on other factors including:

- Which character the player is playing as.
- The number of enemies.
- The state of the player's health.
- How much EX Charge the player has.
- How drunk the character is.

Regardless, the game will always display a prompt that indicates that an EX Action can be performed if available, by having the card glows in the character's color.

---
### EX Boost

After beating Act 1, you will be given a relic that allows you to enter a "transformed" state called EX Boost.
To use the relic, simply right-click it.
When you right-click it, you can not right-click it again this turn (to activate or deactivate EX Boost)

#### While in EX Boost:
- Deal 1 more damage and gain 1 more block for every 3 EX Charge when you enter this state.
- Draw an extra card at the start of the turn
- Gain 1 Energy at the start of the turn
- Taking damage will no longer reduce the EX Gauge
- If you are in a stance, the increases gained from EX Gauge remains while you are in EX Boost, even if the gauge decreases.
- you will gain access to some powers and special attacks not available in your regular state
- You can gain more benefit through Powers and Relics

#### However, there are downsides. While in EX Boost:
- Your EX Gauge drain at the end of your turn
- Your attacks will not charge the EX Gauge

When your EX Gauge is empty while in EX Boost, you will exit this state at the start of your next turn (before draw) or after you play your next card.

---
### Reaction Card

This is a new type of card.
Reaction cards' effects will not activate on use. Instead, this card will be automatically activated when the condition written on it are met, then discard itself (in some case, exhaust itself if it has the Exhaust keyword)

When you first draw this card, it will be retained for one turn. When the card is no longer retained, it will glow with a red color.

Playing a Reaction Card will let you draw another card. Don't worry if it has the keyword "Exhaust" on it; that just means the card will exhaust itself when it activates.

Some trigger that may activate Reaction Card:
- Receiving unblocked attack
- Receiving/Applying a buff/debuff
- Drawing a card
- Exhausting a card

Do note that Reaction Cards activate from far left in your hand to far right in your hand, and that each trigger can only activate one card.

---
### Evasion, Parry and Resist

These are some keywords that appear on Reaction Card.
- Evasion: Damage negation that scales with Dexterity.
- Parry: Damage negation that scales with Strength.
- Resist: Damage reduction that scales with both Strength AND Dexterity.

---
### Drunk
A new kind of power. For each stack, attacks give one more EX Charge (if the owner is a PlaywrightCharacter, else it deals +2 damage for each stack). However, attacks has a 10% chance of dealing 0 damage for each stack.

Each PlaywrightCharacter has a different amount of Drunk they can stack. Non-PlaywrightCharacter will have 5.

At max drunk, getting more drunk will give you one "Smashed" card for each stack over the limit. On drawn, "Smashed" will reduce your Drunk level to 0, and you lose 1 energy if you had more than 1 Drunk level.

Drink safe, ya hear?