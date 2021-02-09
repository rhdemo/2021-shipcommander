import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPort, Port } from 'app/shared/model/port.model';
import { PortService } from './port.service';

@Component({
  selector: 'jhi-port-update',
  templateUrl: './port-update.component.html',
})
export class PortUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    latitude: [],
    longtitude: [],
  });

  constructor(protected portService: PortService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ port }) => {
      this.updateForm(port);
    });
  }

  updateForm(port: IPort): void {
    this.editForm.patchValue({
      id: port.id,
      name: port.name,
      latitude: port.latitude,
      longtitude: port.longtitude,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const port = this.createFromForm();
    if (port.id !== undefined) {
      this.subscribeToSaveResponse(this.portService.update(port));
    } else {
      this.subscribeToSaveResponse(this.portService.create(port));
    }
  }

  private createFromForm(): IPort {
    return {
      ...new Port(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longtitude: this.editForm.get(['longtitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPort>>): void {
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
}
