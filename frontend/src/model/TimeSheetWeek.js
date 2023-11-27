class TimeSheetWeek {

    weekNumber
    from
    to
    hoursWorked
    directSupervisionHours

    constructor(){
        this.reset()
    }

    reset(){
        this.weekNumber = 0
        this.from = ""
        this.to = ""
        this.hoursWorked = 0
        this.directSupervisionHours = 0
    }

    init(timeSheetWeek){
        if(timeSheetWeek.weekNumber) this.weekNumber = timeSheetWeek.weekNumber
        else delete this.weekNumber
        if(timeSheetWeek.from) this.from = timeSheetWeek.from
        else delete this.from
        if(timeSheetWeek.to) this.to = timeSheetWeek.to
        else delete this.to
        if(timeSheetWeek.hoursWorked) this.hoursWorked = timeSheetWeek.hoursWorked
        else delete this.hoursWorked
        if(timeSheetWeek.directSupervisionHours) this.directSupervisionHours = timeSheetWeek.directSupervisionHours
        else delete this.directSupervisionHours
    }

}

export default TimeSheetWeek
