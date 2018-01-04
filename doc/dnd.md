# Do Not Disturb

Slack has a very straight forward and useful Do Not Disturb feature.

Here are the four options:

- `dnd.info`: DnD info for a specific user
- `dnd.teamInfo`: DnD info for a whole team
- `dnd.setSnooze`: turn on DnD for a user for a specified time
- `dnd.endSnooze`: turn off DnD for a user

> **N.B.**: the last two are currently not accessible by bot. Trying to
use the methods will result in an error.

The `clj-slack-client.dnd` module has just a few functions for accessing
these api methods and avoiding some basic pitfalls.

### `time-difference`

The first is a basic function to investigate time stamps. It takes two
time stamps and returns the difference between them. The time stamps
are intended to be in seconds, which is what the Slack API responses
return. The value returned is a hashmap with fields:

- `:min` minutes
- `:sec` seconds
- `:str` a string taking the form `<min>:<sec>` (i.e. _clock-time_)

### `get-user-dnd` and `get-team-dnd`

These functions get the API response maps for the API methods `dnd.info`
and `dnd.teamInfo` respectively. The responses from these can be fed to
`time-delta` to figure out the time remaining on a DnD timer.

### `time-delta`

This function uses `time-difference` to return the time remaining on a
DnD timer given a response from the `get-dnd-response` function. It
takes a start timestamp and a response map. A useful way to get this
starting timestamp is the `:ts` atom from a message sent by Slack.

### `set-snooze`

Starts DnD for a particular number of minutes. Needs to be called by a
non-bot.

### `end-snooze` and `end-dnd`

These end the Snooze and DnD modes, or do nothing if sessions are not
activated. These need to be called by a non-bot.

## Using `dnd` with a bot

A useful way to utilize these functions for a bot is by wrapping some
of the functions to leverage the Slack messages. The
[pomodoro bot](https://github.com/the-mikedavis/pomodoro/blob/master/src/pomodoro/status.clj)
has some wrappers which deal solely with messages and responses. The
wrapper function converts the results of these API calls to strings,
which are then sent by the bot to the asking user.
