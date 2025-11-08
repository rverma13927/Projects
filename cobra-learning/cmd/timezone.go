/*
Copyright © 2025 NAME HERE <EMAIL ADDRESS>
*/
package cmd

import (
	"fmt"
	"log"

	"github.com/spf13/cobra"
)

// timezoneCmd represents the timezone command
var timezoneCmd = &cobra.Command{
	Use:   "timezone",
	Short: "A brief description of your command",
	Long: `A longer description that spans multiple lines and likely contains examples
and usage of using your command. For example:

Cobra is a CLI library for Go that empowers applications.
This application is a tool to generate the needed files
to quickly create a Cobra application.`,
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("timezone called")

		// guard: ensure the user provided a timezone argument
		if len(args) == 0 {
			fmt.Fprintln(cmd.OutOrStderr(), "Usage: timezone <ZONE>")
			return
		}

		timeZone := args[0]

        dateFlag, _ := cmd.Flags().GetString("date")
		currentTime, err := getTimeInTimezone(timeZone, dateFlag);
		if err != nil {
			log.Fatalln("The timezone string is invalid: " + err.Error())
		}
		fmt.Println(currentTime)
	},
}

func init() {
	rootCmd.AddCommand(timezoneCmd)
    timezoneCmd.PersistentFlags().String("date","","return the date in a time zone in a given format")
	// Here you will define your flags and configuration settings.

	// Cobra supports Persistent Flags which will work for this command
	// and all subcommands, e.g.:
	// timezoneCmd.PersistentFlags().String("foo", "", "A help for foo")

	// Cobra supports local flags which will only run when this command
	// is called directly, e.g.:
	// timezoneCmd.Flags().BoolP("toggle", "t", false, "Help message for toggle")
}
