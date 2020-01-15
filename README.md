Written by Ian McKechnie (1051662)
Email: imckechn@uoguelph.ca
Cis2430a3
Completed on 18/10/19

I confirm I did write this myself and have not stolen code from anywhere or body.

TO RUN: It's run automatically so don't worry. You may have to take out the test folder because it does not like when the Junit tests are in there.

***For the TA who's marking this

Wanted to give you a description for the algorithm to make it easier to understand how to read it.
So my program's main first creates an ArrayList of chambers. It makes 5 of them off the bat.

Then it calls a functiont that creates a linked hash map of all the passages.
This method first generates the Passage that the troop starts the dungeon in. This on is unique in that at one end it does not connect to anything.

It then generates the rest of them because they follow a similar pattern.
This algorithm works by first looping through the chamber list. If the chamber that is being checked has a exit that does not have a passage generated for it. It will then loop
through the chamber list again and find another chamber (obviously doesnt select the same chamber twice) and then it connects them (with the method of that name sake). This creates
a passage and links the two chambers. It then calls another method that tells that other chamber that it has a connect and adds that same passage to it's list of passages in the
hash map. Once all the connecting is done it returns and adds that passage to the chambers passage list in the hashmap. Then repeats and checks that chamber for more open passages.
If all of them are full, it checks the next chamebr.

Hope this makes marking easier!



