# Module 1


## Reflection 1

I think i have a lot to improve when 
writing my code. For example i could try  
using a more  descriptive and meaningful names for 
classes, methods, variables, and parameters to enhance 
readability and understanding of the code.
Maintain consistent formatting and indentation throughout the codebase to improve readability and maintainability.

## Reflection 2

1. After writing unit tests, I feel more confident in the correctness and validity
of my code.Regarding the number of unit tests in a class, there isn't a strict rule, but it's generally advisable to have enough tests to cover various scenarios
, including normal cases, edge cases, and error cases.


2. while the new functional test suite follows the same structure as existing ones, there are opportunities to improve code cleanliness and maintainability by addressing potential issues such as code duplication, clarity of test cases, handling magic numbers/strings, error handling, and readability. By refactoring and applying these improvements, the code quality can be enhanced without reducing its effectiveness.

# Module 2

## Reflection

1. The `ProductController` class was using field injection by directly annotating the service field with `@Autowired`. I refactored the code to use constructor injection instead of field injection. Constructor injection is generally considered 
a better practice as it makes dependencies explicit and facilitates better unit testing. And i got the wrong argument on assertEquals(). I did assertEquals(actualValue, expectedValue) where it is supposed to be the other way around.
2. I think my code has met the definition of CI/CD. I integrated my app with testing tools and static code analysis tools
   such as SonarQube, JaCoCo, JUnit, and OSSF Scorecard to identify potential issues, enforce coding standards, and maintain code quality. 
   For CD, i used koyeb to deploy my app to automate the deployment process so that changes can be quickly and reliably deployed to production.

# Module 3

## Reflection

## Solid Principle Application

- SRP
  - The CarRepository class has a single responsibility, 
   which is to handle the persistence operations related to the Car entity. Each method within the class is responsible for a specific operation related to car data management (create, find, update, delete).

- OCP
  - In `ProductService.java`, the create method can check if `productId` has a value of null and will generate a random UUID.

- LSP
  - In `ProductController.java` The classes `CarController` and `ProductController` are separated and now focuses on
  a specific type object and there is no inheritance relationship between them.

## Principles Applied to the Project

- Single Responsibility Principle (SRP): Each class in the project has a single responsibility or reason to change. For example, a CarRepository class is responsible for handling persistence operations related to cars, while a CarController class is responsible for managing HTTP requests and responses related to cars.
- Open/Closed Principle (OCP): The project is designed to be open for extension but closed for modification. For instance, new features can be added by extending existing classes or introducing new ones without altering the existing codebase. 
- Liskov Substitution Principle (LSP): Subtypes must be substitutable for their base types without affecting the correctness of the program. The separation of ProductController and CarController ensures that each controller can be replaced with a subtype (e.g., a specialized car controller) without impacting the overall behavior of the system.

## Disadvantages of Not Applying SOLID Principles

- Without SRP, OCP, and LSP, the project may become difficult to maintain. Changes or updates in one part of the codebase may require modifications in multiple other places, increasing the risk of errors and bugs.