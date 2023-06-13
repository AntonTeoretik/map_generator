# Coordinate systems

World has Y axe up, unit = size of a cell

Each floor has its own coordinate system. Cells are numbered from upper left corner

Objects initially has scaled world coordinates, but when loading from they converted to the world coordinates

Light maps should correspond to world coordinate as well, but since we have only integral indices, we have to scale 
again 

In total we need these transformations:

```
floor : (Int, Int) -> world : Vector2
lightMap : (Int, Int) -> world : Vector2
```






