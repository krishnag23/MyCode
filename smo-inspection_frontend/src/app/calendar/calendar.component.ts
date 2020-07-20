import { Component, OnInit } from '@angular/core';
import * as _ from 'underscore';

import { ActivatedRoute, Router } from '@angular/router';

import { RestApiService } from './../services/rest-api.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  providers: [DatePipe]
})

export class CalendarComponent implements OnInit {

  constructor(
    private apiService: RestApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private ngxService: NgxUiLoaderService,
    private datePipe: DatePipe
  ) { }

  userData: any;

  monthNames: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  daysNames: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  temp = false;

  myDate = new Date();
  currentMonthNumber: number;
  currentMonth: string;
  currentDayNumber: number;
  currentDay: string;
  level: number;
  currentYear: number;
  curentDate: number;
  nextDate: number;
  levelData: any;
  report: string[];
  searchFilter = false;
  celanderFilter = false;
  isNextMonth: boolean;
  groupedArray: any;
  isCurrentDate: boolean;
  previousRecord: number;
  lastReportedDate: any;
  lastReportedMonth: number;
  emptyNext = false;
  emptyCurrentDate = false;
  currentFullDate: any;
  lastDateToCurrentDate: any;
  nextDay: string;
  nextMonthNumber: number;
  nextDayNumber: number;
  nextMonth: string;
  showNextMonth: boolean;
  nextDateWithData = true;
  searchFilterArray: any;

  ngOnInit() {
    this.ngxService.start();
    this.activatedRoute.queryParamMap.subscribe(paramMap => this.level = parseInt(paramMap.get('level')));

    this.activatedRoute.data.subscribe((data: { userClaims: any }) => {
      this.userData = data.userClaims;
    });


    this.apiService.getListOfReport(this.level).subscribe(resp => {
      //console.log(resp);
      this.levelData = resp;
      this.report = this.levelData.data;
      const length = this.levelData.data.length;
      // Get Current Date
      this.currentMonthNumber = this.myDate.getMonth();
      this.currentMonth = this.monthNames[this.currentMonthNumber];
      this.currentYear = this.myDate.getUTCFullYear();
      this.currentDayNumber = this.myDate.getDay();
      this.currentDay = this.daysNames[this.currentDayNumber];
      this.currentDayNumber = this.currentDayNumber + 1 === 8 ? 0 : this.currentDayNumber + 1;
      this.curentDate = this.myDate.getDate();
      let nextDay = new Date();
      nextDay.setDate(nextDay.getDate() + 1);
      this.nextDate = nextDay.getDate();
      this.nextDayNumber = nextDay.getDay();
      this.nextDay = this.daysNames[this.nextDayNumber];
      this.nextMonthNumber = nextDay.getMonth();
      this.nextMonth = this.monthNames[this.nextMonthNumber];

      if (length === 0) {
        this.getCurrentAndNextDate();
      } else {
        this.celanderFilter = true;

        this.report.sort((a, b) => (b['reportingDate'].localeCompare(a['reportingDate']))).reverse();
        for (let x = 0; x < length; x++) {
          this.report[x]['reportingDate'] = (this.report[x]['reportingDate'].split('T'))[0];
        }

        this.searchFilterArray = _.groupBy(this.report, 'reportingDate');
        let tempGroupedArray: any = _.groupBy(this.report, 'reportingDate');
        this.lastReportedDate = tempGroupedArray[Object.keys(tempGroupedArray)[Object.keys(tempGroupedArray).length - 1]][0].reportingDate;
        let tempArray = Object.keys(tempGroupedArray);
        for (const [index, date] of tempArray.entries()) {
          if (index + 1 <= tempArray.length) {
            let startDate = tempArray[index]
            let endDate = tempArray[index + 1]
            let dates = this.dateDiffrance(startDate, endDate);
            for (const missedDate of dates) {
              const resultMorning = {};
              resultMorning['hasMorning'] = false;
              resultMorning['hasAfternoon'] = false;
              resultMorning['level'] = this.level;
              resultMorning['isMissedMorning'] = true;
              resultMorning['reportingDate'] = missedDate;
              tempGroupedArray[missedDate] = [resultMorning]
            }
          }
        }
        this.groupedArray = tempGroupedArray;

        // Test Last Reporting Date To Current Date
        let curDate = new Date();

        let nextDay = new Date(curDate);
        nextDay.setDate(nextDay.getDate() + 1);
        let todayDate = nextDay.toISOString().slice(0, 10);

        let datesNext = this.dateMissingDiffrance(this.lastReportedDate, todayDate);
        let tempDataNext: any = [];
        for (const missedDate of datesNext) {
          const resultMorning = {};
          let i = 0;
          resultMorning['hasMorning'] = false;
          resultMorning['hasAfternoon'] = false;
          resultMorning['level'] = this.level;
          resultMorning['reportingDate'] = missedDate;
          tempDataNext.push(resultMorning);
          i++;
        }
        this.lastDateToCurrentDate = tempDataNext;
        if (this.lastDateToCurrentDate.length === 0) {
          this.isCurrentDate = true;
          this.previousRecord = Object.keys(this.groupedArray).length;
        }
      }
      this.ngxService.stop();
      window.scrollTo(0, document.body.scrollHeight);
    });
  }

  goToBottom() {
    window.scrollTo(0, document.body.scrollHeight);
  }

  dateDiffrance(startDate: moment.MomentInput, endDate: moment.MomentInput) {
    let dateArray = [];
    let currDate = moment(startDate).startOf('day');
    let lastDate = moment(endDate).startOf('day');
    while (currDate.add(1, 'days').diff(lastDate) < 0) {
      let temp = moment(currDate.clone().toDate()).format('YYYY-MM-DD');
      dateArray.push(temp);
    }
    return dateArray;
  }

  // Test Missing Record //
  dateMissingDiffrance(startDate: moment.MomentInput, endDate: moment.MomentInput) {
    let dateArray = [];
    let currDate = moment(startDate).startOf('day');
    let lastDate = moment(endDate).startOf('day');
    while (currDate.add(1, 'days').diff(lastDate) < 0) {
      let temp = moment(currDate.clone().toDate()).format('YYYY-MM-DD');
      dateArray.push(temp);
    }
    return dateArray;
  }

  getCurrentAndNextDate() {
    this.emptyCurrentDate = true;
    this.nextDateWithData = false;
    this.curentDate = this.myDate.getDate();
    this.currentMonthNumber = this.myDate.getMonth();
    this.currentMonth = this.monthNames[this.currentMonthNumber];
    this.currentYear = this.myDate.getUTCFullYear();
    this.currentDayNumber = this.myDate.getDay();
    this.currentYear = this.myDate.getFullYear();
    this.currentDay = this.daysNames[this.currentDayNumber];
    // tslint:disable-next-line: max-line-length
    this.currentFullDate = ('' + (((this.currentMonthNumber + 1) < 10) ? ('0' + (this.currentMonthNumber + 1)) : (this.currentMonthNumber + 1)) + '-' + ((this.curentDate < 10) ? ('0' + this.curentDate) : this.curentDate) + '-' + this.currentYear);


    // Next Date
    let nextDay = new Date();
    nextDay.setDate(nextDay.getDate() + 1);
    this.nextDate = nextDay.getDate();
    this.nextDayNumber = nextDay.getDay();
    this.nextDay = this.daysNames[this.nextDayNumber];
    this.nextMonthNumber = nextDay.getMonth();
    this.nextMonth = this.monthNames[this.nextMonthNumber];
    if (this.currentMonthNumber !== this.nextMonthNumber) {
      this.showNextMonth = true;
    }
  }

  search() {
    this.searchFilter = true;
    this.celanderFilter = false;
    this.nextDateWithData = false;
    this.emptyCurrentDate = false;
  }

  celanderView() {
    this.nextDateWithData = true;
    this.searchFilter = false;
    this.celanderFilter = true;
  }

  showEditView(recordData: any) {

    const reporter = recordData.userId.split('##')[1];
    const queryParams = {
      reportId: recordData.id,
      level: this.level,
      reportDate: recordData.reportingDate,
      reporter
    };

    if (recordData.hasMorning) {
      queryParams['morningReport'] = true;
    }
    if (recordData.hasAfternoon) {
      queryParams['afternoonReport'] = true;
    }

    this.router.navigate(['edit'], {
      skipLocationChange: false,
      queryParams
    });
  }

}

