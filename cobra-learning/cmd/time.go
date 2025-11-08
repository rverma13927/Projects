package cmd

import (
	"time"
)

func getTimeInTimezone(timeZone string, format string) (string, error) {
	 Location, error:=time.LoadLocation(timeZone);
	if error!=nil {
		return "", error;
	}
	currentTime := time.Now().In(Location)
	if format != "" {
		return currentTime.Format(format), nil;
	}
	return  currentTime.Format(time.RFC1123), nil;
}