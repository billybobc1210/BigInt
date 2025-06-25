This is a class for encapsulating arbitrarily large integers and providing the ability to perform mathematical operations on them like 
addition, subtraction, multiplcation, division, modulo arithmetic, absolute value and negation, etc. Essentially, 
it does what Java's built-in BigInteger class does.  

Of course, BigInteger does it much more efficiently, so the purpose of this project is not to try to do it better 
but rather as an exercise in seeing how it could be done by other means.  Essentially, the way this class performs 
the core operations of addition, subtraction, multiplication and division is to emulate in software how we learned to 
do these operations in grade school.  Examples:

Addition  

If we are adding 3829 + 398 we would line the two numbers on top of each other like:

    3829  
   + 398
   -----
    
Then starting on the right we'd add the 9 and the 8 to get 17.  We'd record the 7 in the 1's column and carry the 1 to
the 10's column, etc., finally getting 4227 as the final answer.
