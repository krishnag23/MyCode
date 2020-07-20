import { QuestionDetails } from "../app/QuestionDetails";

export class Responce{
    public hasMorning: boolean;
    public hasafternoon:boolean;
    public level:string;
    public reportingDate:string;
    public userId:string;
    public questionDetails:QuestionDetails;
constructor(
    hasMorning: boolean,
    hasafternoon:boolean,
    level:string,
    reportingDate:string,
    userId:string,
    questionDetails:QuestionDetails
    ){   
        this.hasMorning=hasMorning;
        this.hasafternoon=hasafternoon;
        this.level=level;
        this.reportingDate=reportingDate;
        this.userId=userId;
        this.questionDetails=questionDetails;
    }
}


