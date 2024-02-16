# Template
The model which will hold the workout plans . It consists of multiple exercises, each with its sets.
### Fields
- id (number, primary key)
- name (string)
- isBaseTemplate (bool)
  - used for knowing whether the current template was created by the user and needs to appear in the BrowseTemplates Screen
  - it is false for templates used for recording a specific workout
