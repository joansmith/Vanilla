/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.world.generator.normal.structure.stronghold;

import java.util.List;

import com.google.common.collect.Lists;

import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.world.generator.normal.structure.stronghold.StrongholdDoor.EmptyDoorway;
import org.spout.vanilla.world.generator.structure.PieceCuboidBuilder;
import org.spout.vanilla.world.generator.structure.Structure;
import org.spout.vanilla.world.generator.structure.StructurePiece;
import org.spout.vanilla.world.generator.structure.WeightedNextStructurePiece;

public class StrongholdSpiralStaircase extends WeightedNextStructurePiece {
	private static final WeightedNextPieceCache DEFAULT_NEXT = new WeightedNextPieceCache().
			add(StrongholdLibrary.class, 5).
			add(StrongholdLargeIntersection.class, 5).
			add(StrongholdSpiralStaircase.class, 15).
			add(StrongholdRoom.class, 15).
			add(StrongholdPrison.class, 15).
			add(StrongholdIntersection.class, 15).
			add(StrongholdStaircase.class, 15).
			add(StrongholdTurn.class, 15);

	public StrongholdSpiralStaircase(Structure parent) {
		super(parent, DEFAULT_NEXT);
	}

	@Override
	public boolean canPlace() {
		final PieceCuboidBuilder box = new PieceCuboidBuilder(this);
		box.setMinMax(-1, -7, -1, 5, 5, 5);
		return !box.intersectsLiquids();
	}

	@Override
	public void place() {
		// General shape
		final PieceCuboidBuilder box = new PieceCuboidBuilder(this);
		box.setPicker(new StrongholdBlockMaterialPicker(getRandom()));
		box.setMinMax(0, -6, 0, 4, 4, 4).toggleIgnoreAir().fill();
		box.toggleIgnoreAir();
		// Place the doors
		StrongholdDoor.getRandomDoor(this, getRandom()).place(1, 1, 0);
		new EmptyDoorway(this).place(1, -5, 4);
		// Place the steps
		setBlockMaterial(2, 0, 1, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -1, 1, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, 0, 1, VanillaMaterials.SLAB);
		setBlockMaterial(1, -1, 2, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -2, 3, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -1, 3, VanillaMaterials.SLAB);
		setBlockMaterial(2, -2, 3, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(3, -3, 3, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(3, -2, 3, VanillaMaterials.SLAB);
		setBlockMaterial(3, -3, 2, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(3, -4, 1, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(3, -3, 1, VanillaMaterials.SLAB);
		setBlockMaterial(2, -4, 1, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -5, 1, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -4, 1, VanillaMaterials.SLAB);
		setBlockMaterial(1, -5, 2, VanillaMaterials.STONE_BRICK);
		setBlockMaterial(1, -5, 3, VanillaMaterials.SLAB);
	}

	@Override
	public void randomize() {
	}

	@Override
	public List<StructurePiece> getNextPieces() {
		final StructurePiece piece = getNextPiece();
		piece.setPosition(position.add(rotate(0, -6, 5)));
		piece.setRotation(rotation);
		piece.randomize();
		return Lists.newArrayList(piece);
	}

	@Override
	public BoundingBox getBoundingBox() {
		return new BoundingBox(transform(0, -6, 0), transform(4, 4, 4));
	}
}
