# Refactoring to Patterns – Replace State Altering Conditionals with State

*Replace State Altering Conditionals with State* is the refactoring described in Chapter 7 of the book [Refactoring to Patterns](https://industriallogic.com/xp/refactoring/).

The commits in this repository try to mimic the steps described in the example section of the chapter. The first commit represents the initial state with the state altering conditionals in the ```SystemPermission``` class.

We report below the mechanics of this refactoring.

### Mechanics

1. The *context class* is a class that contains the *original state field*, a field that gets assigned to or compared against a family of constants during state transitions. Apply [Replace Type Code with Class](https://www.industriallogic.com/xp/refactoring/typeCodeWithClass.html) on the original state field such that its type becomes a class. We’ll call that new class the *state superclass*.
   The context class is known as State:Context and the state superclass as State:State in [Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns).

   ✅ Compile.

2. Each constant in the state superclass now refers to an instance of the state superclass. Apply [Extract Subclass](https://refactoring.guru/extract-subclass) to produce one subclass (known as State:ConcreteState in [Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns)) per constant, then update the constants in the state superclass so that each refers to the correct subclass instance of the state superclass. Finally, declare the state superclass to be abstract.

   ✅ Compile.

3. Find a context class method that changes the value of the original state field based on state transition logic. Copy this method to the state superclass, making the simplest changes possible to make the new method work. (A common, *simple* change is to pass the context class to the method in order to have code call methods on the context class.) Finally, replace the body of the context class method with a delegation call to the new method.

   ✅ Compile and test.

   Repeat this step for every context class method that changes the value of the original state field based on state transition logic.

4. Choose a state that the context class can enter, and identify which state superclass methods make this state transition to other states. Copy the identified method(s), if any, to the subclass associated with the chosen state and remove all unrelated logic.
   *Unrelated logic usually includes verifications of a current state or logic that transitions to unrelated states.*

   ✅ Compile and test.

   Repeat for all states the context class can enter.

5. Delete the bodies of each of the methods copied to the state superclass during step 3 to produce an empty implementation for each method.

   ✅ Compile and test.