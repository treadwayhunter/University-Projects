# Last Haven
## Description
A village survival simulator with RTS elements.
Villagers can be clicked, and given tasks. Tasks are queued. 

## Goal
* Maximize the number of villagers.
* Grow the size of the village. Make lots of buildings.
* Have fun

# Development Thoughts
* Non-villager entities
  * Entities such as trees, rocks, bushes, etc will likely need to be rendered in the correct order?
    * Well... not if they take up exactly 1 unit
    * If they do overlap 1 unit, they need to be sorted based on their y value, smaller y values rendered first
    * Villagers could be placed in here too. It might just get crazy.
* Villagers
  * The VillagerSystem will need to render every frame, and the villager's needs updated every few frames.
    * Using deltaTime, we could make it more based on time than frames.
      * Every 10 seconds of time, update villager needs. But 10 seconds can't be hit exactly.
  