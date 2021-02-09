import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPort } from 'app/shared/model/port.model';

@Component({
  selector: 'jhi-port-detail',
  templateUrl: './port-detail.component.html',
})
export class PortDetailComponent implements OnInit {
  port: IPort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ port }) => (this.port = port));
  }

  previousState(): void {
    window.history.back();
  }
}
