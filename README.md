# Vacuum-Service
# Vacuum Cleaner Robots Management Simulation

This project aims to implement a web application that simulates the management of vacuum cleaner robots. The application allows users to add vacuum cleaners to the system, control their state, schedule operations, and log any errors that occur during scheduled tasks.

## Project Overview

The project is divided into the following main components:

1. **User Management**: Managing user accounts and permissions is handled by a separate repository. You can find the User Management System [here]([https://github.com/your-username/user-management-system]). 
   - Users can only interact with vacuum cleaners that they added to the system. 
2. **Vacuum Management**: Managing vacuum cleaner robots, their states, and scheduling operations like starting, stopping, and discharging.

Each user has specific permissions to perform certain actions on the vacuum cleaners. Users without specific permissions cannot execute or access certain operations.

### Features

- **Vacuum Entity Attributes**:
  - `name`: The name of the vacuum cleaner.
  - `type`: The type of vacuum cleaner.
  - `description`: A short description of the vacuum cleaner.
  - `id`: Unique identifier.
  - `status`: Current status of the vacuum cleaner (ON, OFF, DISCHARGING).
  - `addedBy`: Reference to the user who added the vacuum cleaner.
  - `active`: Boolean indicating whether the vacuum cleaner is active in the system.

- **Available Operations**:
  - **SEARCH**: Retrieve a list of active vacuum cleaners for the logged-in user.
  - **START**: Start the vacuum cleaner (if it's not already running).
  - **STOP**: Stop the vacuum cleaner (if it's currently running).
  - **DISCHARGE**: Discharge the vacuum cleaner (if it's currently stopped).
  - **ADD**: Add a new vacuum cleaner to the system.
  - **REMOVE**: Mark a vacuum cleaner as removed from the system (only if it's stopped).

- **Permissions**:
  - `can_search_vacuum`
  - `can_start_vacuum`
  - `can_stop_vacuum`
  - `can_discharge_vacuum`
  - `can_add_vacuum`
  - `can_remove_vacuums`

- **Scheduled Operations**: Users can schedule START, STOP, or DISCHARGE operations for a future date and time. If the operation fails, an error message is logged.

### Vacuum Cleaner States and Operations

- **START**: A vacuum cleaner can only be started if it's in the STOPPED state. The operation takes 15+ seconds to complete and switches the vacuum's state to RUNNING.
  
- **STOP**: A vacuum cleaner can only be stopped if it's in the RUNNING state. The operation takes 15+ seconds to complete and switches the vacuum's state to STOPPED.

- **DISCHARGE**: A vacuum cleaner can only be discharged if it's in the STOPPED state. The process takes 30+ seconds, switching the state to DISCHARGING midway and back to STOPPED after completion. After three RUNNING-STOPPED cycles, a vacuum cleaner automatically discharges.

### Error Logging

Failed operations are logged in a new table (`ErrorMessage`), which records:
  - Date of the failed operation
  - Vacuum cleaner ID
  - Scheduled operation
  - Error message

### Frontend Implementation

- **Vacuum Search Page**: Displays all the user's vacuum cleaners using the SEARCH operation. A filter form allows users to refine the search results by name, status, and date range.
  
- **Vacuum Addition Page**: A form to add a new vacuum cleaner. Users provide only the name, and the system handles the rest.

- **Error Log Page**: Displays a list of errors for failed scheduled operations on the user's vacuum cleaners.

