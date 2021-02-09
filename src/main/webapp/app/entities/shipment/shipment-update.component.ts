import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IShipment, Shipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from './shipment.service';
import { IPort } from 'app/shared/model/port.model';
import { PortService } from 'app/entities/port/port.service';
import { IShip } from 'app/shared/model/ship.model';
import { ShipService } from 'app/entities/ship/ship.service';

type SelectableEntity = IPort | IShip;

@Component({
  selector: 'jhi-shipment-update',
  templateUrl: './shipment-update.component.html',
})
export class ShipmentUpdateComponent implements OnInit {
  isSaving = false;
  startports: IPort[] = [];
  endports: IPort[] = [];
  ships: IShip[] = [];

  editForm = this.fb.group({
    id: [],
    startPortId: [],
    endPortId: [],
    shipId: [],
  });

  constructor(
    protected shipmentService: ShipmentService,
    protected portService: PortService,
    protected shipService: ShipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipment }) => {
      this.updateForm(shipment);

      this.portService
        .query({ filter: 'shipment-is-null' })
        .pipe(
          map((res: HttpResponse<IPort[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPort[]) => {
          if (!shipment.startPortId) {
            this.startports = resBody;
          } else {
            this.portService
              .find(shipment.startPortId)
              .pipe(
                map((subRes: HttpResponse<IPort>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPort[]) => (this.startports = concatRes));
          }
        });

      this.portService
        .query({ filter: 'shipment-is-null' })
        .pipe(
          map((res: HttpResponse<IPort[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPort[]) => {
          if (!shipment.endPortId) {
            this.endports = resBody;
          } else {
            this.portService
              .find(shipment.endPortId)
              .pipe(
                map((subRes: HttpResponse<IPort>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPort[]) => (this.endports = concatRes));
          }
        });

      this.shipService
        .query({ filter: 'shipment-is-null' })
        .pipe(
          map((res: HttpResponse<IShip[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IShip[]) => {
          if (!shipment.shipId) {
            this.ships = resBody;
          } else {
            this.shipService
              .find(shipment.shipId)
              .pipe(
                map((subRes: HttpResponse<IShip>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IShip[]) => (this.ships = concatRes));
          }
        });
    });
  }

  updateForm(shipment: IShipment): void {
    this.editForm.patchValue({
      id: shipment.id,
      startPortId: shipment.startPortId,
      endPortId: shipment.endPortId,
      shipId: shipment.shipId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipment = this.createFromForm();
    if (shipment.id !== undefined) {
      this.subscribeToSaveResponse(this.shipmentService.update(shipment));
    } else {
      this.subscribeToSaveResponse(this.shipmentService.create(shipment));
    }
  }

  private createFromForm(): IShipment {
    return {
      ...new Shipment(),
      id: this.editForm.get(['id'])!.value,
      startPortId: this.editForm.get(['startPortId'])!.value,
      endPortId: this.editForm.get(['endPortId'])!.value,
      shipId: this.editForm.get(['shipId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipment>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
