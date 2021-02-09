import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShip, Ship } from 'app/shared/model/ship.model';
import { ShipService } from './ship.service';

@Component({
  selector: 'jhi-ship-update',
  templateUrl: './ship-update.component.html',
})
export class ShipUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    vesselType: [],
  });

  constructor(protected shipService: ShipService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ship }) => {
      this.updateForm(ship);
    });
  }

  updateForm(ship: IShip): void {
    this.editForm.patchValue({
      id: ship.id,
      name: ship.name,
      vesselType: ship.vesselType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ship = this.createFromForm();
    if (ship.id !== undefined) {
      this.subscribeToSaveResponse(this.shipService.update(ship));
    } else {
      this.subscribeToSaveResponse(this.shipService.create(ship));
    }
  }

  private createFromForm(): IShip {
    return {
      ...new Ship(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      vesselType: this.editForm.get(['vesselType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShip>>): void {
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
