import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISensorReadings } from 'app/shared/model/sensor-readings.model';

@Component({
  selector: 'jhi-sensor-readings-detail',
  templateUrl: './sensor-readings-detail.component.html',
})
export class SensorReadingsDetailComponent implements OnInit {
  sensorReadings: ISensorReadings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensorReadings }) => (this.sensorReadings = sensorReadings));
  }

  previousState(): void {
    window.history.back();
  }
}
