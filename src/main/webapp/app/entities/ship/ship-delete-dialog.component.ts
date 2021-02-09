import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShip } from 'app/shared/model/ship.model';
import { ShipService } from './ship.service';

@Component({
  templateUrl: './ship-delete-dialog.component.html',
})
export class ShipDeleteDialogComponent {
  ship?: IShip;

  constructor(protected shipService: ShipService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shipListModification');
      this.activeModal.close();
    });
  }
}
