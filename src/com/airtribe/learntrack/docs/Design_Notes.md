## Advantages of using ArrayList over Array

- The main advantage of an ArrayList over Array is **dynamic resizing** and **built-in manipulation methods**.
- **Dynamic Sizing:** ArrayLists grow and shrink automatically when you **add** or **remove** items. This will be an advantage for this project as we keep adding and removing entities **dynamically based on user input** and there is no need to know the final size ahead of time.
- **Helper Functions:** ArrayLists provide ready to use methods like `add()`, `remove()`, `set()` etc., which can be used on the go while writing code. This **saves a lot of time** and helps us to concentrate on business logic rather than writing repetitive boilerplate code.
- **Easy Printing:** Printing ArrayList to the console is straight forward and easier than printing Array elements. As we have to print the details a lot many times, ArrayList will be **more convenient** over Array here.

## Use of Static members

- Static members are mostly used in **utility classes** and **Constants** like to declare and generate ids, menu contents etc.,
- **Global Access and Convenience:** Declaring variables and methods as `static` makes them **globally accessible** without even creating object of that class. This also helps with **memory efficiency**.
- **Objectless Access:** You can access static variables as well as static methods directly using the class name without wasting resources to instantiate an object.
- **Memory efficiency:** In case of variables, without static, every single object instance you create allocates its own duplicate copy of that constant in memory. Marking it `static` ensures only **one single copy** exists in memory at the class level, shared by all instances. And in case of static methods, as it saves us creating new objects to invoke the method, it saves us Heap memory and minimizes Garbage Collection overhead.

## Use of Inheritance

- **Inheritance** is a mechanism which allows one class to acquire the **properties (fields)** and **behaviors (methods)** of another class. This project makes use of this mechanism in `Student` class, where Student class **is-a** child of `Person` class and through this it re-uses the fields of Person class.
- Inheritance improves **code re-usability**, thus avoiding boilerplate code. In the current code Student class re-uses `id`, `firstName`, `lastName` and `email` fields from Person class. Also, if in future we have to introduce Trainer class, then we can re-use the person class's fields by making Trainer as child of Person class.