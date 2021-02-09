import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISensorReadings, SensorReadings } from 'app/shared/model/sensor-readings.model';
import { SensorReadingsService } from './sensor-readings.service';
import { IContainer } from 'app/shared/model/container.model';
import { ContainerService } from 'app/entities/container/container.service';

@Component({
  selector: 'jhi-sensor-readings-update',
  templateUrl: './sensor-readings-update.component.html',
})
export class SensorReadingsUpdateComponent implements OnInit {
  isSaving = false;
  containers: IContainer[] = [];

  editForm = this.fb.group({
    id: [],
    temperature: [],
    latitude: [],
    longtitude: [],
    humidityRate: [],
    containerId: [],
  });

  constructor(
    protected sensorReadingsService: SensorReadingsService,
    protected containerService: ContainerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensorReadings }) => {
      this.updateForm(sensorReadings);

      this.containerService.query().subscribe((res: HttpResponse<IContainer[]>) => (this.containers = res.body || []));
    });
  }

  updateForm(sensorReadings: ISensorReadings): void {
    this.editForm.patchValue({
      id: sensorReadings.id,
      temperature: sensorReadings.temperature,
      latitude: sensorReadings.latitude,
      longtitude: sensorReadings.longtitude,
      humidityRate: sensorReadings.humidityRate,
      containerId: sensorReadings.containerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sensorReadings = this.createFromForm();
    if (sensorReadings.id !== undefined) {
      this.subscribeToSaveResponse(this.sensorReadingsService.update(sensorReadings));
    } else {
      this.subscribeToSaveResponse(this.sensorReadingsService.create(sensorReadings));
    }
  }

  private createFromForm(): ISensorReadings {
    return {
      ...new SensorReadings(),
      id: this.editForm.get(['id'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longtitude: this.editForm.get(['longtitude'])!.value,
      humidityRate: this.editForm.get(['humidityRate'])!.value,
      containerId: this.editForm.get(['containerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensorReadings>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IContainer): any {
    return item.id;
  }
}
