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