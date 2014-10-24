package com.july.lineclear;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class GameCellGroup extends Group {
	GameCell cells[][];
	final static int ROW = 8;
	final static int COLUMN = 7;
	final static int EMPTY = -1;

	int lastRow = EMPTY;
	int lastColumn = EMPTY;

	public GameCellGroup() {
		Array<Integer> array = new Array<Integer>();
		for (int i = 0; i < 28; i++) {
			array.add((int) (Math.random() * 10));
		}
		array.addAll(array);
		array.shuffle();
		cells = new GameCell[ROW][COLUMN];
		for (int i = 0; i < ROW; i++)
			for (int j = 0; j < COLUMN; j++) {
				cells[i][j] = Pools.obtain(GameCell.class);
				cells[i][j].setValue(array.get(j + i * COLUMN), i, j);
				addActor(cells[i][j]);
			}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (lastRow != EMPTY && lastColumn != EMPTY)
			batch.draw(AssetManager.getInstance().selected,
					cells[lastRow][lastColumn].getX(),
					cells[lastRow][lastColumn].getY(),
					cells[lastRow][lastColumn].getWidth(),
					cells[lastRow][lastColumn].getHeight());
	}

	public boolean clicked(int row, int column, Array<Vector2> array) {
		// 如果选中空白处，则不做任何处理
		if (cells[row][column].getType() == EMPTY)
			return false;

		// 如果之前没有选择，或者当前选择的和之前选择的不是相同的图片，则更新选择位置
		if ((lastRow == EMPTY && lastColumn == EMPTY)
				|| (lastRow == row && lastColumn == column)
				|| cells[lastRow][lastColumn].getType() != cells[row][column]
						.getType()) {
			lastRow = row;
			lastColumn = column;
			return false;
		}
		boolean ret;
		if (row > lastRow)
			ret = judge(row, column, lastRow, lastColumn, array);
		else
			ret = judge(lastRow, lastColumn, row, column, array);

		if (!ret) {
			lastRow = row;
			lastColumn = column;
			return false;
		}

		cells[lastRow][lastColumn].addMoveOutAction();
		cells[row][column].addMoveOutAction();
		lastRow = EMPTY;
		lastColumn = EMPTY;
		return true;
	}

	public boolean judge(int maxRow, int maxColumn, int minRow, int minColumn,
			Array<Vector2> array) {
		if (maxRow == minRow) {
			if (judgeRow(maxRow, minColumn, maxColumn))
				return initArray(array, 0, maxRow, maxColumn, maxRow,
						maxColumn, minRow, minColumn);
		}

		if (judgeRelate(maxRow, maxColumn, minRow, minColumn, array))
			return true;
		if (judgeLeft(maxRow, maxColumn, minRow, minColumn, array))
			return true;
		if (judgeRight(maxRow, maxColumn, minRow, minColumn, array))
			return true;
		if (judgeUpDown(maxRow, maxColumn, minRow, minColumn, array))
			return true;
		if (judgeUp(maxRow, maxColumn, minRow, minColumn, array))
			return true;
		if (judgeDown(maxRow, maxColumn, minRow, minColumn, array))
			return true;

		return false;
	}

	/**
	 * 判断该列是否有元素
	 * 
	 * @param maxRow
	 *            行上界
	 * @param minRow
	 *            行下界
	 * @param column
	 *            列
	 * @return true表示全是空白，false表示存在元素
	 */
	public boolean judgeColumn(int maxRow, int minRow, int column) {
		for (int i = minRow + 1; i < maxRow; i++) {
			if (cells[i][column].getType() != EMPTY)
				return false;
		}
		return true;
	}

	public boolean judgeRow(int row, int maxColumn, int minColumn) {
		if (maxColumn < minColumn) {
			int temp = maxColumn;
			maxColumn = minColumn;
			minColumn = temp;
		}
		for (int i = minColumn + 1; i < maxColumn; i++) {
			if (cells[row][i].getType() != EMPTY)
				return false;
		}
		return true;
	}

	public boolean judgeLeft(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		int i;
		if (maxColumn >= minColumn) {
			if (getLeftIndexOfRow(maxRow, minColumn, maxColumn) != minColumn) {
				return false;
			}
			i = minColumn - 1;
		} else {
			if (getLeftIndexOfRow(minRow, maxColumn, minColumn) != maxColumn)
				return false;
			i = maxColumn - 1;
		}

		while (i >= 0) {
			if (cells[minRow][i].getType() != EMPTY
					|| cells[maxRow][i].getType() != EMPTY) {
				return false;
			}
			if (judgeColumn(maxRow, minRow, i)) {
				return initArray(array, 2, minRow, i, maxRow, maxColumn,
						minRow, minColumn);
			}
			i--;
		}
		// out of bound
		return initArray(array, 2, minRow, i, maxRow, maxColumn, minRow,
				minColumn);
	}

	public boolean judgeRight(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		int i;
		if (maxColumn >= minColumn) {
			if (getRightIndexOfRow(minRow, minColumn, maxColumn) != maxColumn)
				return false;
			i = maxColumn + 1;
		} else {
			if (getRightIndexOfRow(maxRow, maxColumn, minColumn) != minColumn)
				return false;
			i = minColumn + 1;
		}

		while (i < COLUMN) {
			if (cells[minRow][i].getType() != EMPTY
					|| cells[maxRow][i].getType() != EMPTY) {
				return false;
			}
			if (judgeColumn(maxRow, minRow, i)) {
				return initArray(array, 2, minRow, i, maxRow, maxColumn,
						minRow, minColumn);
			}
			i++;
		}
		return initArray(array, 2, minRow, i, maxRow, maxColumn, minRow,
				minColumn);
	}

	/**
	 * 相向比较，即出现的线将是Z L和 7字形
	 * 
	 * @param maxRow
	 * @param maxColumn
	 * @param minRow
	 * @param minColumn
	 * @param array
	 * @return
	 */
	public boolean judgeRelate(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		if (maxColumn > minColumn) {
			int l = getLeftIndexOfRow(maxRow, minColumn, maxColumn);
			if (l == minColumn && judgeColumn(maxRow, minRow, minColumn)) {
				// 竖线 右横线 即L型
				return initArray(array, 1, maxRow, minColumn, maxRow,
						maxColumn, minRow, minColumn);
			}
			int i;
			for (i = minColumn + 1; i < maxColumn; i++) {
				if (cells[minRow][i].getType() != EMPTY)
					return false;
				else if (i < l)
					continue;
				else if (judgeColumn(maxRow, minRow, i)) {
					// 右横线 竖线 右横线 即Z型
					return initArray(array, 2, minRow, i, maxRow, maxColumn,
							minRow, minColumn);
				}
			}
			if (i == maxColumn && cells[minRow][i].getType() == EMPTY
					&& judgeColumn(maxRow, minRow, i)) { // 右横线 竖线 即7型
				return initArray(array, 1, minRow, i, maxRow, maxColumn,
						minRow, minColumn);
			}
		} else if (maxColumn == minColumn) {
			if (judgeColumn(maxRow, minRow, minColumn)) {// 竖线
				return initArray(array, 0, minRow, minColumn, maxRow,
						maxColumn, minRow, minColumn);
			}
		} else {
			int r = getRightIndexOfRow(maxRow, maxColumn, minColumn);
			if (r == minColumn && judgeColumn(maxRow, minRow, minColumn)) {
				// 竖线 左横线 即翻L型
				return initArray(array, 1, maxRow, minColumn, maxRow,
						maxColumn, minRow, minColumn);
			}
			int i;
			for (i = minColumn - 1; i > maxColumn; i--) {
				if (cells[minRow][i].getType() != EMPTY)
					break;
				else if (i > r)
					continue;
				else if (judgeColumn(maxRow, minRow, i)) {
					// 左横线 竖线 左横线
					return initArray(array, 2, minRow, i, maxRow, maxColumn,
							minRow, minColumn);
				}
			}
			if (i == maxColumn && cells[minRow][i].getType() == EMPTY
					&& judgeColumn(maxRow, minRow, i)) {
				// 左横线 竖线
				return initArray(array, 1, minRow, i, maxRow, maxColumn,
						minRow, minColumn);
			}
		}

		return false;
	}

	public boolean judgeUp(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		if (getUpIndexOfColumn(minRow, maxRow, maxColumn) != minRow)
			return false;
		int i = minRow - 1;
		while (i >= 0) {
			if (cells[i][minColumn].getType() != EMPTY
					|| cells[i][maxColumn].getType() != EMPTY)
				return false;
			if (judgeRow(i, maxColumn, minColumn)) {
				return initArrayV(array, i, maxRow, maxColumn, minRow,
						minColumn);
			}
			i--;
		}
		return initArrayV(array, i, maxRow, maxColumn, minRow, minColumn);
	}

	public boolean judgeDown(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		if (getDownIndexOfColumn(minRow, maxRow, minColumn) != maxRow)
			return false;
		int i = maxRow + 1;
		while (i < ROW) {
			if (cells[i][minColumn].getType() != EMPTY
					|| cells[i][maxColumn].getType() != EMPTY)
				return false;

			if (judgeRow(i, maxColumn, minColumn)) {
				return initArrayV(array, i, maxRow, maxColumn, minRow,
						minColumn);
			}
			i++;
		}

		return initArrayV(array, i, maxRow, maxColumn, minRow, minColumn);
	}

	public boolean judgeUpDown(int maxRow, int maxColumn, int minRow,
			int minColumn, Array<Vector2> array) {
		int u = getUpIndexOfColumn(minRow, maxRow, maxColumn);
		if (u == maxRow)
			return false;
		int i = minRow + 1;
		while (i < u) {
			if (cells[i][minColumn].getType() != EMPTY)
				return false;
			i++;
		}
		while (i < maxRow) {
			if (judgeRow(i, maxColumn, minColumn))
				return initArrayV(array, i, maxRow, maxColumn, minRow,
						minColumn);
		}

		return false;
	}

	public int getUpIndexOfColumn(int smallerRow, int biggerRow, int column) {
		for (int i = biggerRow - 1; i >= smallerRow; i--) {
			if (cells[i][column].getType() != EMPTY)
				return i + 1;
		}
		return smallerRow;
	}

	public int getDownIndexOfColumn(int smallerRow, int biggerRow, int column) {
		for (int i = smallerRow + 1; i <= biggerRow; i++) {
			if (cells[i][column].getType() != EMPTY)
				return i - 1;
		}

		return biggerRow;
	}

	public int getLeftIndexOfRow(int row, int smallerColumn, int biggerColumn) {
		for (int i = biggerColumn - 1; i >= smallerColumn; i--) {
			if (cells[row][i].getType() != EMPTY)
				return i + 1;
		}
		return smallerColumn;
	}

	public int getRightIndexOfRow(int row, int smallerColumn, int biggerColumn) {
		for (int i = smallerColumn + 1; i <= biggerColumn; i++) {
			if (cells[row][i].getType() != EMPTY)
				return i - 1;
		}
		return biggerColumn;
	}

	public boolean initArray(Array<Vector2> array, int number, int row,
			int column, int maxRow, int maxColumn, int minRow, int minColumn) {
		array.clear();
		Vector2 v0 = Pools.obtain(Vector2.class);
		v0.x = minRow;
		v0.y = minColumn;
		array.add(v0);
		if (number == 2) {
			Vector2 v1 = Pools.obtain(Vector2.class);
			v1.x = minRow;
			v1.y = column;
			array.add(v1);
			Vector2 v2 = Pools.obtain(Vector2.class);
			v2.x = maxRow;
			v2.y = column;
			array.add(v2);
		} else if (number == 1) {
			Vector2 v1 = Pools.obtain(Vector2.class);
			v1.x = row;
			v1.y = column;
			array.add(v1);
		}
		Vector2 v3 = Pools.obtain(Vector2.class);
		v3.x = maxRow;
		v3.y = maxColumn;
		array.add(v3);
		return true;
	}

	public boolean initArrayV(Array<Vector2> array, int row, int maxRow,
			int maxColumn, int minRow, int minColumn) {
		array.clear();
		Vector2 v0 = Pools.obtain(Vector2.class);
		v0.x = minRow;
		v0.y = minColumn;
		array.add(v0);
		Vector2 v1 = Pools.obtain(Vector2.class);
		v1.x = row;
		v1.y = minColumn;
		array.add(v1);
		Vector2 v2 = Pools.obtain(Vector2.class);
		v2.x = row;
		v2.y = maxColumn;
		array.add(v2);
		Vector2 v3 = Pools.obtain(Vector2.class);
		v3.x = maxRow;
		v3.y = maxColumn;
		array.add(v3);
		return true;
	}
}
