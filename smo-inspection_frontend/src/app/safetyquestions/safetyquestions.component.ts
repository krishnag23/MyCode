import { Component, OnInit, Inject } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { RestApiService } from './../services/rest-api.service';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import * as moment from 'moment';

import { QuestionDetails } from '../QuestionDetails';
import { MatSelect } from '@angular/material/select';
import { NgxUiLoaderService } from 'ngx-ui-loader';

import { NotificationService } from './../notification.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogData {
  info: string;
}
@Component({
  selector: 'app-safetyquestions',
  templateUrl: './safetyquestions.component.html',
  styleUrls: ['./safetyquestions.component.css']
})
export class SafetyquestionsComponent implements OnInit {

  userData: any;
  totalData: any;
  questionMainHeading: string[];
  mainHeading: string;
  numbarOfQuestion: number;
  questionSubHeading: string;
  questionMaster: string[];
  counter: number;
  masterUsers: any;
  id: number;
  userName: string;
  emailId: string;
  indexQuestion: number[] = new Array();
  level: number;
  // Form Element
  hasAfternoon = false;
  hasMorning = false;
  resolve: boolean;
  notResolve: boolean;
  resolveNo: string;
  notResolveNo: string;
  formData: FormGroup;
  submitButton = false;
  nextButton = true;
  responce: Response[] = [];
  questionArray: QuestionDetails[] = [];
  statusOfAction: string;
  unsafeAction: string;
  issueCounter = 0;
  reportingDate: string;
  date: Date;
  morningQuestion: number = 0;
  afterNoonQuestion: number = 0;
  enableNext: boolean = false;
  enableSubmit: boolean = false;
  todaysDate: Date


  info: string;


  constructor(
    private formBuilder: FormBuilder,
    private apiService: RestApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private ngxService: NgxUiLoaderService,
    private notifyService: NotificationService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.ngxService.start();
    window.scrollTo(0, 0);
    // tslint:disable-next-line: radix
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.level = parseInt(paramMap.get('level')));
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.reportingDate = paramMap.get('reportDate'));
    this.activatedRoute.queryParamMap.subscribe(paramMap => {
      if (paramMap['params']['morningReport'] !== undefined) {
        this.hasMorning = true;
      }
      if (paramMap['params']['afternoonReport'] !== undefined) {
        this.hasAfternoon = true;
      }
    });

    this.activatedRoute.data.subscribe((data: { userClaims: any }) => {
      this.userData = data.userClaims;
    });

    this.date = new Date(this.reportingDate);
    this.todaysDate = new Date();


    this.apiService.getData().subscribe(data => {
      this.totalData = data;
      this.getQuestionData(-1);
      this.getMasterUsers();
      this.ngxService.stop();
    });
    this.formData = this.formBuilder.group({
      reply: ['', [Validators.required, Validators.minLength(1)]],
      unsafeAactAction: ['', [Validators.required, Validators.minLength(1)]],
      actionRequired: ['', [Validators.required, Validators.minLength(1)]],
      targetCompletionDate: ['', [Validators.required, Validators.minLength(1)]],
      isIssueResolved: ['', [Validators.required, Validators.minLength(1)]],
      personInCharge: ['', [Validators.required, Validators.minLength(1)]],
      dateOfActionCompleted: ['', ],
      questionId: ['', ],
      subHeadingId: ['', ],
      mainHeadingId: ['', ]
    });
  }
  getQuestionData(counter: number) {
    this.enableNext = false;
    this.nextButton = true;
    counter = counter + 1;
    console.log(this.totalData);
    this.hasMorning = this.hasMorning;
    this.hasAfternoon = this.hasAfternoon;

    this.numbarOfQuestion = this.totalData.data.questionMainHeading.length;
    this.questionMainHeading = this.totalData.data.questionMainHeading[counter];

    //this.mainHeadingId=this.totalData.data.questionMainHeading[counter].id;

    if (this.hasMorning && this.totalData.data.questionMainHeading[counter].hasMorning) {
      this.mainHeading = this.totalData.data.questionMainHeading[counter].mainHeading;
    }
    if (this.hasAfternoon && this.totalData.data.questionMainHeading[counter].hasAfternoon) {
      this.mainHeading = this.totalData.data.questionMainHeading[counter].mainHeading;
    }

    this.questionSubHeading = this.totalData.data.questionMainHeading[counter].questionSubHeading;
    this.counter = counter;
    if (this.counter === this.numbarOfQuestion - 1) {
      this.nextButton = false;
      this.submitButton = true;
    }

    //Calculating No of question per page
    for (let x = 0; x < this.questionSubHeading.length; x++) {
      let tempData: any = this.questionSubHeading[x];
      for (let a = 0; a < tempData.questionMaster.length; a++) {
        if (tempData.questionMaster[a].hasAfternoon == true) {
          this.afterNoonQuestion = this.afterNoonQuestion + 1
        }
        if (tempData.questionMaster[a].hasMorning) {
          this.morningQuestion = this.morningQuestion + 1
        }
      }
    }
  }

  //Issue in Selecting No Option
  onNoClick(indexQuestion: number, replyOption: string) {
    if (replyOption === 'NO') {
      this.indexQuestion.push(indexQuestion);
    }
    else {
      const index: number = this.indexQuestion.indexOf(indexQuestion);
      if (index !== -1) {
        this.indexQuestion.splice(index, 1);
      }
    }
    console.log(this.indexQuestion);
  }

  onClick() {
    if (this.checkDataValidation())
      return;

    window.scrollTo(0, 0);
    this.formData.reset();
    console.log(this.formData);
    this.getQuestionData(this.counter);

  }

  checkDataValidation(): boolean {
    for (let i = 0; i < this.questionArray.length; i++) {
      if (this.questionArray[i].reply === 'NO') {
        if (this.questionArray[i].unsafeAactAction.trim() === '') {
          this.notifyService.showWarning('Please fill Unsafe act/action field')
          return true;

        }
        else if (this.questionArray[i].actionRequired.trim() === '') {
          this.notifyService.showWarning('Please fill Action rquired field')
          return true;

        }
        else if (this.questionArray[i].personInCharge.trim() === '') {
          this.notifyService.showWarning('Please fill Person in charge field')
          return true;

        }
        else if (this.questionArray[i].targetCompletionDate === '' && this.questionArray[i].dateOfActionCompleted === '') {
          this.notifyService.showWarning('Please select specific date of issue')
          return true;
        }
      }
    }
  }

  issueResolved(id: any) {
    this.resolve = true;
    this.notResolve = false;
    this.notResolveNo = '';
    this.resolveNo = id;
  }

  issueNotResolved(id: any) {
    this.resolve = false;
    this.notResolve = true;
    this.resolveNo = '';
    this.notResolveNo = id;
  }

  showToasterWarning() {
    this.notifyService.showWarning('All Fields Are Required')
  }

  onFormChange(el: any, replyOption: string, mainHeadingId: number, subHeadingId: number) {
    // Need To Remove 0 index Object
    // const dataPresent = el.getAttribute('data-question-id');
    const index = this.questionArray.findIndex(e => e.questionId === el);
    if (index > -1) {
      this.questionArray.splice(index, 1);
    }

    // tslint:disable-next-line: max-line-length
    this.questionArray.push(new QuestionDetails('', '', true, mainHeadingId, '', el, replyOption, this.formData.value.statusOfAction, subHeadingId, '', ''));
    console.log(this.questionArray);
    if (this.questionArray.length == this.afterNoonQuestion && this.hasAfternoon == true && this.enableSubmit == false && this.submitButton == false) {
      this.enableNext = true;
      this.nextButton = false;
    }
    if (this.questionArray.length == this.morningQuestion && this.hasMorning == true && this.enableSubmit == false && this.submitButton == false) {
      this.enableNext = true;
      this.nextButton = false;
    }
    //For Submit
    if (this.questionArray.length == this.afterNoonQuestion && this.hasAfternoon == true && this.submitButton == true) {
      this.enableSubmit = true;
      this.submitButton = false;
      this.enableNext = false;
    }
    if (this.questionArray.length == this.morningQuestion && this.hasMorning == true && this.submitButton == true) {
      this.enableSubmit = true;
      this.submitButton = false;
      this.enableNext = false;
    }
  }

  onNoSetData(event: any, question: any, type: string) {

    const index = this.questionArray.findIndex(e => e.questionId === question);
    if (index > -1) {
      if (type === 'action') {
        this.questionArray[index].unsafeAactAction = event.target.value;

      }
      else {
        this.questionArray[index].actionRequired = event.target.value;

      }
    }
  }
  issueResolvedOrNot(event: any, question: any) {
    // const dataPresent = question.getAttribute('data-question-id');
    const index = this.questionArray.findIndex(e => e.questionId === question);
    if (this.resolve && index > -1) {
      // tslint:disable-next-line: max-line-length
      const selectedDate = (event.value).toLocaleDateString().split('/')[2] + '-' +  ((((event.value).toLocaleDateString().split('/')[0]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[0])) : ((event.value).toLocaleDateString().split('/')[0])) + '-' +  ((((event.value).toLocaleDateString().split('/')[1]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[1])) : ((event.value).toLocaleDateString().split('/')[1]));
      this.questionArray[index].dateOfActionCompleted = selectedDate;
      this.questionArray[index].isIssueResolved = true;
      this.questionArray[index].targetCompletionDate = '';
    }
    if (this.notResolve && index > -1) {
      // tslint:disable-next-line: max-line-length
      const selectedDate = (event.value).toLocaleDateString().split('/')[2] + '-' +  ((((event.value).toLocaleDateString().split('/')[0]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[0])) : ((event.value).toLocaleDateString().split('/')[0])) + '-' +  ((((event.value).toLocaleDateString().split('/')[1]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[1])) : ((event.value).toLocaleDateString().split('/')[1]));
      this.questionArray[index].targetCompletionDate = selectedDate;
      this.questionArray[index].isIssueResolved = false;
      this.questionArray[index].dateOfActionCompleted = '';
    }
  }

  changePerson(event: MatSelect, quesId: any) {
    // const dataPresent = quesId.getAttribute('data-question-id');
    const index = this.questionArray.findIndex(e => e.questionId == quesId);
    if (index > -1) {
      this.questionArray[index].personInCharge = event.value;
    }

  }
  openDialog(information: string): void {
    this.info = information;
    const dialogRef = this.dialog.open(DialogInfo, {
      width: '280px',
      data: { info: this.info }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  getMasterUsers() {
    this.masterUsers = this.totalData.data.masterUsers;
  }

  onSubmit() {
    if (this.checkDataValidation())
      return;

    const result = {};
    result['hasMorning'] = this.hasMorning;
    result['hasafternoon'] = this.hasAfternoon;
    result['level'] = this.level;

    const temp = new Date();
    // tslint:disable-next-line: max-line-length
    const time = 'T' + ((temp.getUTCHours() < 10) ? ('0' + temp.getUTCHours()) : temp.getUTCHours()) + ':' + ((temp.getUTCMinutes() < 10) ? ('0' + temp.getUTCMinutes()) : temp.getUTCMinutes()) + ':' + ((temp.getUTCSeconds() < 10) ? ('0' + temp.getUTCSeconds()) : temp.getUTCSeconds()) + '.' + ((temp.getUTCMilliseconds() < 10) ? ('00' + temp.getUTCMilliseconds()) : (temp.getUTCMilliseconds() < 100) ? ('0' + temp.getUTCMilliseconds()) : temp.getUTCMilliseconds()) + 'Z';

    let reportDate = this.reportingDate;
    // tslint:disable-next-line: max-line-length
    const reportDate_Date = ((new Date(reportDate).getDate() < 10) ? ('0' + new Date(reportDate).getDate()) : new Date(reportDate).getDate());
    // tslint:disable-next-line: max-line-length
    const reportDate_Month = (((new Date(reportDate).getMonth() + 1) < 10) ? ('0' + (new Date(reportDate).getMonth() + 1)) : (new Date(reportDate).getMonth() + 1));
    const reportDate_Year = new Date(reportDate).getFullYear();

    this.reportingDate = '' + reportDate_Year + '-' + reportDate_Month + '-' + reportDate_Date;

    const reportingDateTime = this.reportingDate + time;

    result['reportingDate'] = moment(temp).toISOString();
    result['dateOfList'] = moment(new Date(reportingDateTime)).toISOString();

    result['userId'] = this.userData.email + '##' + this.userData.name;

    for (let i = 0; i < this.questionArray.length; i++) {
      if (this.questionArray[i].isIssueResolved === false) {
        this.issueCounter = this.issueCounter + 1;
      }
    }
    if (this.issueCounter > 0) {
      result['isIssueInReport'] = 1;
    } else {
      result['isIssueInReport'] = 0;
    }

    result['unresolvedCases'] = this.issueCounter;
    result['questionDetails'] = this.questionArray;

    console.log(result);

    this.apiService.saveReport(result).subscribe(data => {
      if (data['statusMessage'].includes('Already')) {
        this.notifyService.showError('This report is already submitted');
      } else {
        this.router.navigate(['calendar'], { skipLocationChange: false, queryParams: { level: this.level } });
      }
    });
  }
}

@Component({
  selector: 'dialog-info',
  templateUrl: './dialoginfo.html',
})
export class DialogInfo {

  constructor(
    public dialogRef: MatDialogRef<DialogInfo>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
