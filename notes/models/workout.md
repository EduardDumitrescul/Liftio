# Workout
Model that stores a performed workout.
Alongside, its [Template](template.md), it stores the start date and duration

### Attributes
- id (number, primary key)
- templateId (number, references Template)
- startDate (date)
- duration (number)
