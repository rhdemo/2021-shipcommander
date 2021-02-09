import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IContainer, Container } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { IShipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from 'app/entities/shipment/shipment.service';

type SelectableEntity = ICompany | IShipment;

@Component({
  selector: 'jhi-container-update',
  templateUrl: './container-update.component.html',
})
export class ContainerUpdateComponent implements OnInit {
  isSaving = false;
  senders: ICompany[] = [];
  receivers: ICompany[] = [];
  shipments: IShipment[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    senderId: [],
    receiverId: [],
    shipmentId: [],
  });

  constructor(
    protected containerService: ContainerService,
    protected companyService: CompanyService,
    protected shipmentService: ShipmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ container }) => {
      this.updateForm(container);

      this.companyService
        .query({ filter: 'container-is-null' })
        .pipe(
          map((res: HttpResponse<ICompany[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompany[]) => {
          if (!container.senderId) {
            this.senders = resBody;
          } else {
            this.companyService
              .find(container.senderId)
              .pipe(
                map((subRes: HttpResponse<ICompany>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompany[]) => (this.senders = concatRes));
          }
        });

      this.companyService
        .query({ filter: 'container-is-null' })
        .pipe(
          map((res: HttpResponse<ICompany[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompany[]) => {
          if (!container.receiverId) {
            this.receivers = resBody;
          } else {
            this.companyService
              .find(container.receiverId)
              .pipe(
                map((subRes: HttpResponse<ICompany>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompany[]) => (this.receivers = concatRes));
          }
        });

      this.shipmentService.query().subscribe((res: HttpResponse<IShipment[]>) => (this.shipments = res.body || []));
    });
  }

  updateForm(container: IContainer): void {
    this.editForm.patchValue({
      id: container.id,
      description: container.description,
      senderId: container.senderId,
      receiverId: container.receiverId,
      shipmentId: container.shipmentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const container = this.createFromForm();
    if (container.id !== undefined) {
      this.subscribeToSaveResponse(this.containerService.update(container));
    } else {
      this.subscribeToSaveResponse(this.containerService.create(container));
    }
  }

  private createFromForm(): IContainer {
    return {
      ...new Container(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      senderId: this.editForm.get(['senderId'])!.value,
      receiverId: this.editForm.get(['receiverId'])!.value,
      shipmentId: this.editForm.get(['shipmentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContainer>>): void {
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
