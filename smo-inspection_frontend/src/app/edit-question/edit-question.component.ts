import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestApiService } from './../services/rest-api.service';
import { FormBuilder, FormGroup } from '@angular/forms';

import { QuestionEdit } from '../QuestionEdit';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-edit-question',
  templateUrl: './edit-question.component.html',
  styleUrls: ['./edit-question.component.css']
})
export class EditQuestionComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private apiService: RestApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxService: NgxUiLoaderService
  ) { }

  //Array Element
  answerData: any;
  numberOfQuestion: number;
  questionMainHeading: string[];
  numbarOfSection: number;
  mainHeading: string;
  mainHeadinghasMorning: boolean;
  mainHeadinghasAfternoon: boolean;
  section: number = 0;
  questionSubHeading: string;
  formData: FormGroup;
  masterUsers: any;
  nextButton = true;
  submitButton: boolean;
  enableSubmit:boolean=false;

  resolve: boolean;
  notResolve: boolean;
  questionArray: QuestionEdit[] = [];
  issueCounter: number = 0;

  answerObj: any;

  userData: any;
  emailId: string;
  reportId: number;

  isMorningReport = false;
  isAfternoonReport = false;

  level: number;
  reportingDate: string;
  reportReporter: string;
  reportType: string;
  date: Date;

  ngOnInit() {
    this.ngxService.start();
    // tslint:disable-next-line: radix
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.reportId = parseInt(paramMap.get('reportId')));
    // tslint:disable-next-line: radix
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.level = parseInt(paramMap.get('level')));
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.reportingDate = paramMap.get('reportDate'));
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.reportReporter = paramMap.get('reporter'));
    this.activatedRoute.queryParamMap.subscribe(paramMap => {
      if (paramMap['params']['morningReport'] !== undefined) {
        this.isMorningReport = true;
        this.reportType = 'Morning';
      }
      if (paramMap['params']['afternoonReport'] !== undefined) {
        this.isAfternoonReport = true;
        this.reportType = 'Afternoon';
      }
    });

    this.activatedRoute.data.subscribe((data: { userClaims: any }) => {
      this.userData = data.userClaims;
      this.emailId = (this.userData.email).toLowerCase();
    });
	window.scrollTo(0, 0);
	

    this.date = new Date(this.reportingDate);

    this.apiService.getReportForEdit(this.reportId).subscribe(resp => {
      console.log("hhhhhh", resp);
      this.answerData = resp;
      this.numbarOfSection = this.answerData.data.questionMainHeading.length;
      this.getAnswerData(this.section);
      this.masterUsers = this.answerData.data.masterUsers;

      for (let i = 0; i < this.numbarOfSection; i++) {
        for (let j = 0; j < this.answerData.data.questionMainHeading[i].questionSubHeading.length; j++) {
          if (this.answerData.data.questionMainHeading[i].questionSubHeading[j].questionMaster !== null) {
            for (let k = 0; k < this.answerData.data.questionMainHeading[i].questionSubHeading[j].questionMaster.length; k++) {
              this.answerObj = this.answerData.data.questionMainHeading[i].questionSubHeading[j].questionMaster[k].answerDetaiils;
              // tslint:disable-next-line: max-line-length
              this.questionArray.push(new QuestionEdit(this.answerObj.id, this.answerObj.actionRequired, this.answerObj.dateOfActionCompleted, (this.answerObj.dateOfActionCompleted !== '' ? false : true), this.answerData.data.questionMainHeading[i].id, this.answerObj.personInCharge, this.answerData.data.questionMainHeading[i].questionSubHeading[j].questionMaster[k].id, this.answerObj.reply, this.answerObj.statusOfAction, this.answerData.data.questionMainHeading[i].questionSubHeading[j].questionMaster[k].subHeadingId, this.answerObj.targetCompletionDate, this.answerObj.unsafeAction));
            }
          }
        }
      }
      this.ngxService.stop();
    });

    //Form Element
    this.formData = this.formBuilder.group({
      mainHeadingId: ['',],
      hasMorning: ['',],
      hasAfternoon: ['',],
      subHeadingId: ['',],
      reply: ['',],
      unsafeAction: ['',],
      actionRequired: ['',],
      personInCharge: ['',],

      targetCompletionDate: ['',],
      isIssueResolved: ['',],
      dateOfActionCompleted: ['',],
      questionId: ['',],
    });
  }

  getAnswerData(page: number) {
    this.section = page;
    this.questionMainHeading = this.answerData.data.questionMainHeading[page];
    this.mainHeading = this.answerData.data.questionMainHeading[page].mainHeading;
    this.numbarOfSection = this.answerData.data.questionMainHeading.length;
    this.mainHeadinghasMorning = this.answerData.data.questionMainHeading[page].hasMorning;
    this.mainHeadinghasAfternoon = this.answerData.data.questionMainHeading[page].hasAfternoon;
    this.answerData.data.questionMainHeading[page].questionSubHeading;
    this.questionSubHeading = this.answerData.data.questionMainHeading[page].questionSubHeading
    if (this.section === this.numbarOfSection - 1) {
      this.nextButton = false;
      if(this.enableSubmit)
      this.submitButton = true;
    }
  }

  getMasterUsers() {
    this.masterUsers = this.answerData.data.masterUsers;
  }
  issueResolved() {
    this.resolve = true;
    this.notResolve = false;
  }

  issueNotResolved() {
    this.resolve = false;
    this.notResolve = true;
  }
  onClick() {
    window.scrollTo(0, 0);
    this.section = this.section + 1;
    this.getAnswerData(this.section);
  }

  issueResolvedOrNot(event: any, question: any) {
    this.enableSubmit = true;
    if (this.section === this.numbarOfSection-1) {
      if(this.enableSubmit)
      this.submitButton = true;
    }
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
      this.questionArray[index].dateOfActionCompleted = '';
      // tslint:disable-next-line: max-line-length
      const selectedDate = (event.value).toLocaleDateString().split('/')[2] + '-' +  ((((event.value).toLocaleDateString().split('/')[0]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[0])) : ((event.value).toLocaleDateString().split('/')[0])) + '-' +  ((((event.value).toLocaleDateString().split('/')[1]) < 10) ? ('0' + ((event.value).toLocaleDateString().split('/')[1])) : ((event.value).toLocaleDateString().split('/')[1]));
      this.questionArray[index].targetCompletionDate = selectedDate;
      this.questionArray[index].isIssueResolved = false;
    }

  }
  onSubmit() {
    const result = {};
    result['hasMorning'] = this.isMorningReport;
    result['hasafternoon'] = this.isAfternoonReport;
    result['level'] = this.level;

    const temp = new Date();
    // tslint:disable-next-line: max-line-length
    const time = 'T' + ((temp.getUTCHours() < 10) ? ('0' + temp.getUTCHours()) : temp.getUTCHours()) + ':' + ((temp.getUTCMinutes() < 10) ? ('0' + temp.getUTCMinutes()) : temp.getUTCMinutes()) + ':' + ((temp.getUTCSeconds() < 10) ? ('0' + temp.getUTCSeconds()) : temp.getUTCSeconds()) + '.' + ((temp.getUTCMilliseconds() < 10) ? ('00' + temp.getUTCMilliseconds()) : (temp.getUTCMilliseconds() < 100) ? ('0' + temp.getUTCMilliseconds()) : temp.getUTCMilliseconds()) + 'Z';
    const reportingDateTime = this.reportingDate + time;

    result['reportingDate'] = reportingDateTime;

    // result['userId'] = this.userData.email + '##' + this.reportReporter;
    // result['userId'] = this.userData.email + '##' + this.userData.name;

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
    result['updatedOn'] = new Date();

    result['questionDetails'] = this.questionArray;

    console.log(result);

    this.apiService.updateReport(result).subscribe(data => {
      console.log('Data Saved Successfully');
      this.router.navigate(['calendar'], { skipLocationChange: false, queryParams: { level: this.level } });
    });

  }
}
