export class PreViousDate{
        public previousDate: number;
        public previousDay: string;
        public previousMonth: string;
        public hasMorning: boolean;
        public hasAfternoon: boolean;
        public isCurrDate: boolean;
        public issueInReport: boolean;
        public reportDate: string;
    constructor(
        previousDate: number,
        previousDay: string,
        previousMonth: string,
        hasMorning: boolean,
        hasAfternoon: boolean,
        isCurrDate: boolean,
        issueInReport: boolean,
        reportDate: string
        ) {
            this.previousDate = previousDate;
            this.previousDay = previousDay;
            this.previousMonth = previousMonth;
            this.hasMorning = hasMorning;
            this.hasAfternoon = hasAfternoon;
            this.isCurrDate = isCurrDate;
            this.issueInReport = issueInReport;
            this.reportDate = reportDate;
        }
}