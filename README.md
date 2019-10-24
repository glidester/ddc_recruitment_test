# ddc_recruitment_test
A coding assignment from a recruiter 

## Assumptions made
1. Formatting of the summed total is outside the scope of this assignment 
(although there is a hint of a decimal format in the spec of "3.5" it is not explicitly stated, 
and "3.5" to represent an amount of "£3:50" seems an unlikely scenario)
1. I would have considered using some sort of case objects for defining a MenuItem's
temperature (hot/cold) and type (food/drink) for future extension but as I was asked for the simplest 
possible solution I stuck to simple boolean flags to denote these properties.
1. The highest service charge rate applicable to any item on a bill
is the one that is applied to the whole bill (although in a real billing application this may not
always hold true).
1. The service charge rate can never be a negative value such as -10% ( 10% off the bill)
1. No item from the menu can cost less than 0 pence (no 50p money back items)
1. Rounding mode of the service charge should be Half_Up (so half a pence becomes a whole pence)
