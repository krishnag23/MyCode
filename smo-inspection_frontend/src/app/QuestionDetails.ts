export class QuestionDetails{
    public actionRequired: string;
    public dateOfActionCompleted: string;
    public isIssueResolved: boolean;
    public mainHeadingId: number;
    public personInCharge: string;
    public questionId: number;
    public reply: string;
    public statusOfAction:string;
    public subHeadingId:number;
    public targetCompletionDate:string;
    public unsafeAactAction:string;
constructor(
    actionRequired: string,
    dateOfActionCompleted: string,
    isIssueResolved: boolean,
    mainHeadingId: number,
    personInCharge: string,
    questionId: number,
    reply: string,
    statusOfAction:string,
    subHeadingId:number,
    targetCompletionDate:string,
    unsafeAactAction:string
    ) {
        this.actionRequired=actionRequired;
        this.dateOfActionCompleted=dateOfActionCompleted;
        this.isIssueResolved=isIssueResolved;
        this.mainHeadingId=mainHeadingId;
        this.personInCharge=personInCharge;
        this.questionId=questionId;
        this.reply=reply;
        this.statusOfAction=statusOfAction;
        this.subHeadingId=subHeadingId;
        this.targetCompletionDate=targetCompletionDate;
        this.unsafeAactAction=unsafeAactAction;
    }
}