## Map coloring problem

Write a program to color a map using the minimum possible number of colors.

The input should be a list of regions on the map, along with the adjacent regions. The output is a list
of the regions with their assigned colors and the total number of colors used. The input should be checked
for consistency; do not allow ridiculous node numbers or nodes connected to themselves. The worst maps will
be very expensive to color; so try to avoid unnecessary program inefficiencies.


```
begin
    if U = V then print "coloring is completed";
        halt
    else
        pick a vertex v not in U ;
        for C := 1 to N do
            if no neighbor of v is colored with color C then
                add v to U with color C ;
                3-coloring(G, U)
end
```    