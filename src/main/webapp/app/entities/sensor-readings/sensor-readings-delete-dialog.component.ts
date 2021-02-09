import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISensorReadings } from 'app/shared/model/sensor-readings.model';
import { SensorReadingsService } from './sensor-readings.service';

@Component({
  templateUrl: './sensor-readings-delete-dialog.component.html',
})
export class SensorReadingsDeleteDialogComponent {
  sensorReadings?: ISensorReadings;

  constructor(
    protected sensorReadingsService: SensorReadingsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sensorReadingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sensorReadingsListModification');
      this.activeModal.close();
    });
  }
}
