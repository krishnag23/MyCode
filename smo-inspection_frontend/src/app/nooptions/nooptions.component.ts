import { Component, OnInit, Input } from '@angular/core';
@Component({
  selector: 'app-nooptions',
  templateUrl: './nooptions.component.html',
  styleUrls: ['./nooptions.component.css']
})
export class NooptionsComponent implements OnInit {
  public masterUsers: any;
  @Input() masterUsersList: any;
  constructor() { }

  ngOnInit(): void {
    this.getMasterList();
  }
  getMasterList() {
    this.masterUsers = this.masterUsersList;
  }
}
